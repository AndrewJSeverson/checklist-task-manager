package com.severson.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.severson.taskmanager.models.EmailItem;
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
	
	/**
	 * Checks every minute for reminder emails that need to be sent
	 */
	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkForReminderAndDueDateEmails(){
		// first grab reminders that are due for emails
		
		
		// now grab due dates
	
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

}
