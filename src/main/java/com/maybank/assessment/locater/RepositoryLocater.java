package com.maybank.assessment.locater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maybank.assessment.repository.TaskRepository;
import com.maybank.assessment.repository.UserRepository;

@Component
public class RepositoryLocater {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TaskRepository taskRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

}
