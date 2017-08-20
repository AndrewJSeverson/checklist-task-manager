package com.severson.taskmanager.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author andrewseverson
 *
 */
@Entity
@Table(name = "checklist_task", schema = "public")
public class ChecklistTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checklist_task_id")
	private int id;
	
	@Column(name = "description", length = 255, nullable = false)
	private String description;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "checklist_id")
	private Checklist checklist;
	
	@Column(name = "due_date")
	private Date dueDate;
	
	@Column(name = "due_date_email_send")
	private Boolean dueDateEmailSent;;
	
	@Column(name = "reminder_date")
	private Date reminderDate;
	
	@Column(name = "reminder_date_email_send")
	private Boolean reminderDateEmailSent;;
	
	@OneToMany(mappedBy = "checklistTask", cascade=CascadeType.REMOVE, orphanRemoval=true)
	public List<TaskCompletion> taskCompletions;
	
	@Transient
	private boolean taskComplete = false;
	
	public ChecklistTask(){}
	
	public ChecklistTask(String description, Checklist checklist, Date dueDate, Date reminderDate) {
		super();
		this.description = description;
		this.checklist = checklist;
		this.dueDate = dueDate;
		this.reminderDate = reminderDate;
		this.dueDateEmailSent = false;
		this.reminderDateEmailSent = false;
	}

	public ChecklistTask(int id, String description, Checklist checklist, Date dueDate, Date reminderDate) {
		super();
		this.id = id;
		this.description = description;
		this.checklist = checklist;
		this.dueDate = dueDate;
		this.reminderDate = reminderDate;
		this.dueDateEmailSent = false;
		this.reminderDateEmailSent = false;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the checklist
	 */
	public Checklist getChecklist() {
		return checklist;
	}

	/**
	 * @param checklist the checklist to set
	 */
	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * @return the reminder_date
	 */
	public Date getReminderDate() {
		return reminderDate;
	}

	/**
	 * @param reminder_date the reminder_date to set
	 */
	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	/**
	 * @return the taskCompletions
	 */
	public List<TaskCompletion> getTaskCompletions() {
		return taskCompletions;
	}

	/**
	 * @param taskCompletions the taskCompletions to set
	 */
	public void setTaskCompletions(List<TaskCompletion> taskCompletions) {
		this.taskCompletions = taskCompletions;
	}


	/**
	 * @return the taskComplete
	 */
	public boolean isTaskComplete() {
		return taskComplete;
	}

	/**
	 * @param taskComplete the taskComplete to set
	 */
	public void setTaskComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}

	/**
	 * @return the dueDateEmailSent
	 */
	public Boolean getDueDateEmailSent() {
		return dueDateEmailSent;
	}

	/**
	 * @param dueDateEmailSent the dueDateEmailSent to set
	 */
	public void setDueDateEmailSent(Boolean dueDateEmailSent) {
		this.dueDateEmailSent = dueDateEmailSent;
	}

	/**
	 * @return the reminderDateEmailSent
	 */
	public Boolean getReminderDateEmailSent() {
		return reminderDateEmailSent;
	}

	/**
	 * @param reminderDateEmailSent the reminderDateEmailSent to set
	 */
	public void setReminderDateEmailSent(Boolean reminderDateEmailSent) {
		this.reminderDateEmailSent = reminderDateEmailSent;
	}
	

}
