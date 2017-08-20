package com.severson.taskmanager.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.severson.taskmanager.models.ChecklistTask;
import com.severson.taskmanager.models.ChecklistUser;
import com.severson.taskmanager.models.EmailItem;
import com.severson.taskmanager.models.TaskCompletion;
import com.severson.taskmanager.repositories.ChecklistTaskRepository;
import com.severson.taskmanager.repositories.EmailItemRepository;

/**
 * @author andrewseverson
 *
 */
@Component
public class NotificationService {
	
	@Autowired
	EmailService emailService;
	@Autowired
	EmailItemRepository emailItemRepository;
	@Autowired
	ChecklistTaskRepository checklistTaskRepository;
	
	/**
	 * Checks every minute for reminder emails that need to be sent
	 */
	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkForReminderAndDueDateEmails(){
		// first grab reminders that are due for emails
		reminderEmailsToSend(checklistTaskRepository.findReminderEmailsToBeSent(new Date()));
		
		// now grab due dates and send
		dueDateEmailsToSend(checklistTaskRepository.findDueDateEmailsToBeSent(new Date()));
		
	}
	
	/**
	 * Checks every minute for tasks that are now due and emails to send
	 */
	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkForUnsentEmail(){
		// get all emails that are not yet sent
		List<EmailItem> emailItems = emailItemRepository.getUnsentEmailItems();
		// loop and send
		for(EmailItem ei : emailItems){
			emailService.sendSendgridEmail(ei);
		}
	}
	
	/*
	 * HELPERS
	 */
	
	/**
	 * @param reminders
	 */
	private void reminderEmailsToSend(List<ChecklistTask> reminders){
		// loop tasks and users to check for completions
		for(ChecklistTask task : reminders){
			// update task
			task.setReminderDateEmailSent(true);
			checklistTaskRepository.save(task);
			for(ChecklistUser u : task.getChecklist().getChecklistUsers()){
				boolean taskCompleteForUser = false;
				for(TaskCompletion completion : task.getTaskCompletions()){
					// now check if its complete
					if(completion.getChecklistUser().getUserId() == u.getUserId()
							&& completion.getCompletionDate() != null){
						taskCompleteForUser = true;
						break;
					}
				}
				if(!taskCompleteForUser){
					// send email
					emailService.sendEmail("Checklist Task Reminder", 
							"You have a checklist task coming due. Task: " + task.getDescription() + 
							". Due Date: " + task.getDueDate().toString(), u.getUser().getEmail());
							
				}
			}
		}
	}
	
	/**
	 * @param dueDates
	 */
	private void dueDateEmailsToSend(List<ChecklistTask> dueDates){
		// loop tasks and users to check for completions
		for(ChecklistTask task : dueDates){
			task.setDueDateEmailSent(true);
			checklistTaskRepository.save(task);
			for(ChecklistUser u : task.getChecklist().getChecklistUsers()){
				boolean taskCompleteForUser = false;
				for(TaskCompletion completion : task.getTaskCompletions()){
					// now check if its complete
					if(completion.getChecklistUser().getUserId() == u.getUserId()
							&& completion.getCompletionDate() != null){
						taskCompleteForUser = true;
						break;
					}
				}
				if(!taskCompleteForUser){
					// send email
					emailService.sendEmail("Checklist Task Due", 
							"You have a checklist task due. Task: " + task.getDescription() + 
							". Due Date: " + task.getDueDate().toString(), u.getUser().getEmail());
							
				}
			}
		}
	}

}
