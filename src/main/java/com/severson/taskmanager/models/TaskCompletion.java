package com.severson.taskmanager.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author andrewseverson
 *
 */
@Entity
@Table(name = "task_completion", schema = "public")
public class TaskCompletion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_completion_id")
	private int id;
	
	@Column(name = "completion_date")
	private Date completionDate;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "checklist_task_id")
	private ChecklistTask checklistTask;
	
	@ManyToOne
	@JsonIgnore
    @JoinColumn(name = "checklist_user_id")
	private ChecklistUser checklistUser;

	public TaskCompletion(){}
	
	public TaskCompletion(Date completionDate, ChecklistTask checklistTask, ChecklistUser checklistUser) {
		super();
		this.completionDate = completionDate;
		this.checklistTask = checklistTask;
		this.checklistUser = checklistUser;
	}
	
	public TaskCompletion(int id, Date completionDate, ChecklistTask checklistTask, ChecklistUser checklistUser) {
		super();
		this.id = id;
		this.completionDate = completionDate;
		this.checklistTask = checklistTask;
		this.checklistUser = checklistUser;
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
	 * @return the completionDate
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * @param completionDate the completionDate to set
	 */
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * @return the checklistTask
	 */
	public ChecklistTask getChecklistTask() {
		return checklistTask;
	}

	/**
	 * @param checklistTask the checklistTask to set
	 */
	public void setChecklistTask(ChecklistTask checklistTask) {
		this.checklistTask = checklistTask;
	}

	/**
	 * @return the checklistUser
	 */
	public ChecklistUser getChecklistUser() {
		return checklistUser;
	}

	/**
	 * @param checklistUser the checklistUser to set
	 */
	public void setChecklistUser(ChecklistUser checklistUser) {
		this.checklistUser = checklistUser;
	}
	
	
}
