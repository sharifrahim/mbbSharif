package com.maybank.assessment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.maybank.assessment.entity.Task;
import com.maybank.assessment.entity.User;

@Service
public interface ITodoService {
    
    List<User> findAllUsers();
    
    User findUsersById(Integer userId);
    
    User createUser(User user);
    
    /**
     * @param user
     * <p>Delete user and all the task under user</p>
     */
    void deleteUser (User user);
    
    List<Task> findAllTaskByUser(User user);
    
    Task createTask(User user, Task task);
    
    Task updateTask(User user, Task task);
    
    void deleteTask(Task task);
    
    Task findTaskByUserAndTaskId(User user, Integer taskId);
    
    /**
     * @param code
     * @param message
     * <p>Daily file located at c:logFolder/ddMMyyyyMMdd.txt</p>
     * 
     */
    void writeRequestResponseToFile(String code, String message);
    
    String convertToJSON(Object object);
    
    

}
