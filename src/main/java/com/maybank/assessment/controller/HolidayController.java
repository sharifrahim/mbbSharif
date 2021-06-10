package com.maybank.assessment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.maybank.assessment.entity.Holiday;
import com.maybank.assessment.service.ITodoService;

@RestController
public class HolidayController {
    
    @Autowired
    @Qualifier("maybankTodoService")
    ITodoService todoService;
    
    
    private String key = "523a39a0030e453cbff7b0880bf1fac9";
    private String url = "holidays.abstractapi.com";
    private String path = "/v1/";
    private String host = "https";

    /** 
     * @param year
     * @param month
     * @param day
     * @return 
     */
    @GetMapping("/holiday/year/{year}/month/{month}/day/{day}")
    public List<Holiday> findAllHolidayCurrentYear(@PathVariable int year, @PathVariable int month, @PathVariable int day) {
        
        todoService.writeRequestResponseToFile("GET",
                ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        
        
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(host).host(url).path(path)
                .queryParams(getParameterMap(year, month, day)).build();
        RestTemplate restTemplate = new RestTemplate();
        Holiday[] holidays = restTemplate.getForObject(uriComponents.toUriString(), Holiday[].class);
        
        todoService.writeRequestResponseToFile("RESPONSE", todoService.convertToJSON(holidays));

        return new ArrayList<>(Arrays.asList(holidays));

    }
    
    /**
     * @param year
     * @param month
     * @param day
     * @return Map with default key and value + paramter value
     */
    private MultiValueMap<String, String> getParameterMap(Integer year, Integer month, Integer day) {

        MultiValueMap<String, String> parameterMap = new LinkedMultiValueMap<String, String>();
        parameterMap.set("api_key", key);
        parameterMap.set("country", "MY");


        if (year != null)
            parameterMap.set("year", year.toString());
        if (month != null)
            parameterMap.set("month", month.toString());
        if (day != null)
            parameterMap.set("day", day.toString());

        return parameterMap;

    }
   

}
