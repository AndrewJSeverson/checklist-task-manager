package com.severson.taskmanager.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author andrewseverson
 *
 */
@Entity
@Table(name = "checklist", schema = "public")
public class Checklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checklist_id")
	private int id;
	
	@Column(name = "name", length = 255, nullable = false)
	private String name;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "last_update_date")
	private Date lastUpdateDate;
	
	@ManyToOne
    @JoinColumn(name = "checklist_catgory_id")
	private ChecklistCategory checklistCategory;
	
	@OneToMany(mappedBy = "checklist", cascade=CascadeType.REMOVE, orphanRemoval=true)
	public List<ChecklistUser> checklistUsers;
	
	@OneToMany(mappedBy = "checklist", cascade=CascadeType.REMOVE, orphanRemoval=true, fetch=FetchType.EAGER)
	public List<ChecklistTask> checklistTasks;
	
	/**
	 * This transient field will be dynamically set based on the user who is accessing the checklist.
	 * If user 1 requests the data, and they have all tasks complete, then it will be set to true, otherwise false.
	 * This logic is done in the service
	 */
	@Transient
	private boolean checklistComplete;
	
	public Checklist(){}

	public Checklist(int id, String name, Date creationDate, Date lastUpdateDate) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.lastUpdateDate = lastUpdateDate;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the checklistCategory
	 */
	public ChecklistCategory getChecklistCategory() {
		return checklistCategory;
	}

	/**
	 * @param checklistCategory the checklistCategory to set
	 */
	public void setChecklistCategory(ChecklistCategory checklistCategory) {
		this.checklistCategory = checklistCategory;
	}

	/**
	 * @return the checklistTaskUsers
	 */
	public List<ChecklistUser> getChecklistUsers() {
		return checklistUsers;
	}

	/**
	 * @param checklistTaskUsers the checklistTaskUsers to set
	 */
	public void setChecklistUsers(List<ChecklistUser> checklistUsers) {
		this.checklistUsers = checklistUsers;
	}

	/**
	 * @return the checklistTasks
	 */
	public List<ChecklistTask> getChecklistTasks() {
		return checklistTasks;
	}

	/**
	 * @param checklistTasks the checklistTasks to set
	 */
	public void setChecklistTasks(List<ChecklistTask> checklistTasks) {
		this.checklistTasks = checklistTasks;
	}

	/**
	 * @return the checklistComplete
	 */
	public boolean isChecklistComplete() {
		return checklistComplete;
	}

	/**
	 * @param checklistComplete the checklistComplete to set
	 */
	public void setChecklistComplete(boolean checklistComplete) {
		this.checklistComplete = checklistComplete;
	}
	
	
}
