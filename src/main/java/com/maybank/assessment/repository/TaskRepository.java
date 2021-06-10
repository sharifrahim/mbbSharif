package com.maybank.assessment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.maybank.assessment.entity.Task;
import com.maybank.assessment.entity.User;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {
    
   List<Task> findByUser(User user);
   
   Page<Task> findByUser(User user, Pageable pageable);
   
   Task findByUserAndId(User user, Integer id);

}
