package com.severson.taskmanager.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andrewseverson
 *
 */
@Component
public class NotificationService {
	
	/**
	 * Checks every minute for reminder emails that need to be sent
	 */
	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkForReminderEmailsToSend(){
		
	
	}
	
	/**
	 * Checks every minute for tasks that are now due and emails to send
	 */
	@Scheduled(fixedRate = 60000)
	@Transactional
	public void checkForDueDateEmailsToSend(){
		
	
	}

}
