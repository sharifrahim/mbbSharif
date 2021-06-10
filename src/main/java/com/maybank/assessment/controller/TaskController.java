package com.maybank.assessment.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.maybank.assessment.entity.Task;
import com.maybank.assessment.entity.User;
import com.maybank.assessment.exception.ResourceNotFoundException;
import com.maybank.assessment.repository.TaskRepository;
import com.maybank.assessment.service.ITodoService;


@RestController
public class TaskController {
    
    @Autowired
    @Qualifier("maybankTodoService")
    ITodoService todoService;
       
    @Autowired
    TaskRepository taskRepository;
   
    /** 
     * @param userId
     * @param page
     * @return task filtered by user id and in pagination along with link to previous and next page
     * @no
     */
    @GetMapping("/users/{userId}/tasks")
    public CollectionModel<Task>  findAllTasks(@PathVariable int userId, @RequestParam Optional<Integer> page) {
        
        todoService.writeRequestResponseToFile("GET",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        User user = todoService.findUsersById(userId);
        
        Integer paging  = page.orElse(0);
        Integer nextPage  = paging+1;
        Integer prevPage  = paging-1;
        
        Integer size =  10;

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + userId);
        }

        Page<Task> resultList = taskRepository.findByUser(user, PageRequest.of(paging, size));
        
        if (!resultList.hasContent()) {
            throw new ResourceNotFoundException("Task not found within range.");
        }
        
        Page<Task> taskPage = taskRepository.findByUser(user, PageRequest.of(page.orElse(0), 1));
        List<Task> taskList = taskPage.getContent();
        CollectionModel<Task> model = CollectionModel.of(taskList);
        
        // put url next page if there is next page available
        if (prevPage >= 0) {
            Optional<Integer> prevPageOpt = Optional.of(prevPage);
            WebMvcLinkBuilder linkToPrevPage = linkTo(methodOn(this.getClass()).findAllTasks(userId, prevPageOpt));
            model.add(linkToPrevPage.withRel("Previous-Page"));
        }
        
        // put url page to page except on page 0
        if (nextPage < taskPage.getTotalPages()) {
            Optional<Integer> nextPageOpt = Optional.of(nextPage);
            WebMvcLinkBuilder linkToNextPage = linkTo(methodOn(this.getClass()).findAllTasks(userId, nextPageOpt));
            model.add(linkToNextPage.withRel("Next-Page"));
        }
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(taskList));
        
        return model;
    }
    
    /**
     * @param userId
     * @param task
     * @return the uri in header
     */
    @PostMapping("/users/{userId}/tasks")
    public ResponseEntity<Object> createTasks(@Valid @PathVariable int userId, @RequestBody Task task) {
        
        todoService.writeRequestResponseToFile("POST",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString() + todoService.convertToJSON(task));


        User user = todoService.findUsersById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + userId);
        }
        
        task.setFlagActive(true);
        task = todoService.createTask(user, task);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(task.getId())
                .toUri();
        
        todoService.writeRequestResponseToFile("RESPONSE", location.toString());

        return ResponseEntity.created(location).build();
    }
    
    /**
     * @param userId
     * @param taskId
     * @param task
     * @return updated Task info
     */
    @PutMapping("/users/{userId}/tasks/{taskId}")
    public Task updateTask(@PathVariable int userId, @PathVariable int taskId, @RequestBody Task task) {

        todoService.writeRequestResponseToFile("UPDATE",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        User user = todoService.findUsersById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + userId);
        }
        
        task.setId(taskId);
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(task));
        
        return todoService.updateTask(user, task);
    }
    
    /**
     * @param userId
     * @param taskId
     */
    @DeleteMapping("/users/{userId}/tasks/{taskId}")
    public void deleteTask(@PathVariable int userId, @PathVariable int taskId) {

        todoService.writeRequestResponseToFile("DELETE",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString());

        User user = todoService.findUsersById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("user id - " + userId);
        }
        
        Task task = todoService.findTaskByUserAndTaskId(user, taskId);
        
        if(task == null) {
            throw new ResourceNotFoundException("task id - " + taskId);
        }
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(task));
        
        todoService.deleteTask(task);
        
    }
    

}
