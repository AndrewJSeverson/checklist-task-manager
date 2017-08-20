package com.severson.taskmanager.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author andrewseverson
 *
 */
@Entity
@Table(name = "checklist_user", schema = "public")
public class ChecklistUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "checklist_user_id")
	private int id;
	
	@ManyToOne
	@JsonBackReference
    @JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne  
	@JsonIgnore
    @JoinColumn(name = "checklist_id", nullable = false)
	private Checklist checklist;
	
	@Transient
	private String userFullName;
	
	@Transient
	private Integer userId;
	
	public ChecklistUser(){}

	public ChecklistUser(int id, User user, Checklist checklist) {
		super();
		this.id = id;
		this.user = user;
		this.checklist = checklist;
	}
	
	public ChecklistUser(User user, Checklist checklist) {
		super();
		this.user = user;
		this.checklist = checklist;
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
	 * @return the taskUser
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param taskUser the taskUser to set
	 */
	public void setUser(User user) {
		this.user = user;
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
	 * @return the userFullName
	 */
	public String getUserFullName() {
		if(getUser() == null){
			return "No User";
		}
		return getUser().getFirstName() + " " + getUser().getLastName();
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		if(getUser() == null){
			return 0;
		}
		return getUser().getId();
	}
	
	

}
