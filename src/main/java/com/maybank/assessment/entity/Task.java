package com.maybank.assessment.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TASKS")
public class Task {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Integer id;
    
    @NotEmpty
    @Column(name = "TASK_NAME", nullable = false, length = 255)
    private String taskName;
    
    @FutureOrPresent
    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "TASK_DATE", nullable = false)
    private Date taskDate;

    
    @Column(name = "FLAG_ACTIVE", nullable = false, columnDefinition = "CHAR(1)")
    private Boolean flagActive = false;
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
    
    public Task() {
        super();
    }
    
    
    public Task(String taskName, Date taskDate, Boolean flagActive, User user) {
        super();
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.flagActive = flagActive;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Boolean getFlagActive() {
        return flagActive;
    }

    public void setFlagActive(Boolean flagActive) {
        this.flagActive = flagActive;
    }
    
    

}
