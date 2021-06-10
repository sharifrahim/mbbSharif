package com.maybank.assessment.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.assessment.entity.Task;
import com.maybank.assessment.entity.User;
import com.maybank.assessment.locater.RepositoryLocater;
import com.maybank.assessment.service.ITodoService;

@Component("maybankTodoService") 
@Transactional
public class MaybankTodoService implements ITodoService {
    
    @Autowired
    RepositoryLocater repositoryLocater;

    @Override 
    public List<User> findAllUsers() {
        return repositoryLocater.getUserRepository().findAll();
    }
    
    @Override
    public User findUsersById(Integer userId) {

        Optional<User> user = repositoryLocater.getUserRepository().findById(userId);
        return user.isPresent() ? user.get() : null;
    }

    @Override
    public User createUser(User user) {
        return repositoryLocater.getUserRepository().save(user);
    }

    @Override
    public void deleteUser(User user) {

        List<Task> taskList = repositoryLocater.getTaskRepository().findByUser(user);
        repositoryLocater.getTaskRepository().deleteAll(taskList);

        repositoryLocater.getUserRepository().deleteById(user.getId());
    }

    @Override
    public List<Task> findAllTaskByUser(User user) {

        return repositoryLocater.getTaskRepository().findByUser(user);
    }

    @Override
    public Task createTask(User user, Task task) {
       
        task.setFlagActive(true);
        task.setUser(user);
        return repositoryLocater.getTaskRepository().save(task);
    }

    @Override
    public Task updateTask(User user, Task task) {
       
        Task foundTask = repositoryLocater.getTaskRepository().findByUserAndId(user, task.getId());
        if (task.getTaskName() != null) {
            foundTask.setTaskName(task.getTaskName());
        }
        if (task.getFlagActive() != null) {
            foundTask.setFlagActive(task.getFlagActive());
        }
        if (task.getTaskDate() != null) {
            foundTask.setTaskDate(task.getTaskDate());
        }
        return repositoryLocater.getTaskRepository().save(foundTask);
    }

    @Override
    public void deleteTask(Task task) {
        repositoryLocater.getTaskRepository().delete(task);
    }

    @Override
    public Task findTaskByUserAndTaskId(User user, Integer taskId) {
        return repositoryLocater.getTaskRepository().findByUserAndId(user, taskId);
    }

    @Override
    public void writeRequestResponseToFile(String code, String message) {
        
        String line = "\n";
        
        SimpleDateFormat simpleDateFormatFile = new SimpleDateFormat("ddMMyyyy");
        String fileName = simpleDateFormatFile.format(new Date());
        
        SimpleDateFormat simpleDateFormatLog = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.s");
        
        
        String dirPath  = "C:\\logFolder";
        String filePath = dirPath + "\\" + fileName + ".txt";
        
        try {

            Files.createDirectories(Paths.get(dirPath));
            
            Path path = Paths.get(filePath);
            
            if (Files.notExists(path)) {
                Files.createFile(Paths.get(filePath));
            } 

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.append(line);
            writer.append(code);
            writer.append(line);
            writer.append(simpleDateFormatLog.format(new Date()));
            writer.append(line);
            writer.append(message);
            
            writer.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String convertToJSON(Object object) {
       
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    

}
