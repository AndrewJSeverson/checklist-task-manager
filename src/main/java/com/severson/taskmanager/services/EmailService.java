package com.severson.taskmanager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Response;
import com.severson.taskmanager.models.EmailItem;
import com.severson.taskmanager.repositories.EmailItemRepository;

@Service(value="EmailService")
public class EmailService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	EmailItemRepository emailItemRepository;

	public void sendEmail(String subject, String body, String to){
		// first we want to save this message in database
		EmailItem emailItem = new EmailItem(subject, body, to, false);
		emailItem = emailItemRepository.save(emailItem);
		emailItemRepository.save(emailItem);
	}
	
	/**
	 * @param emailItem
	 * @return
	 */
	public void sendSendgridEmail(EmailItem emailItem){
		try{
			SendGrid sendgrid = new SendGrid(
					System.getenv("sendgrid_username"), 
					System.getenv("sendgrid_password"));
			SendGrid.Email email = new SendGrid.Email();
			email.addTo(emailItem.getToEmail());
			email.setFrom("checklist-task-manager@ctm.com");
			email.setSubject(emailItem.getSubject());
			email.setText(emailItem.getBody());
			Response response = sendgrid.send(email);
			if(response.getStatus()){
				emailItem.setSent(true);
				emailItemRepository.save(emailItem);
			}
		} catch(Exception e){
			logger.error("Error sending email", e);
		}
	}
}
