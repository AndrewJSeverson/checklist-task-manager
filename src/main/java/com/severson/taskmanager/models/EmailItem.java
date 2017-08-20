package com.severson.taskmanager.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author andrewseverson
 *
 */
@Entity
@Table(name = "email_item", schema = "public")
public class EmailItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "email_item_id")
	private int id;
	
	@Column(name = "subject", length = 255, nullable = false)
	private String subject;
	
	@Column(name = "body", length = 2550, nullable = false)
	private String body;
	
	@Column(name = "to_email", length = 100, nullable = false)
	private String toEmail;
	
	@Column(name = "sent", nullable = false)
	private Boolean sent;

	public EmailItem(){}
	
	public EmailItem(String subject, String body, String toEmail, Boolean sent) {
		super();
		this.subject = subject;
		this.body = body;
		this.toEmail = toEmail;
		this.sent = sent;
	}
	
	public EmailItem(int id, String subject, String body, String toEmail, Boolean sent) {
		super();
		this.id = id;
		this.subject = subject;
		this.body = body;
		this.toEmail = toEmail;
		this.sent = sent;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the toEmail
	 */
	public String getToEmail() {
		return toEmail;
	}

	/**
	 * @param toEmail the toEmail to set
	 */
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * @return the sent
	 */
	public Boolean getSent() {
		return sent;
	}

	/**
	 * @param sent the sent to set
	 */
	public void setSent(Boolean sent) {
		this.sent = sent;
	}
}
