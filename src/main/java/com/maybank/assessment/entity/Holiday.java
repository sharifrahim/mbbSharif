package com.maybank.assessment.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Holiday {
    
    private String name;
    private String name_local;
    private String language;
    private String description;
    private String country;
    private String location;
    private String type;
    @JsonFormat(pattern="MM/dd/yyyy")
    private Date date;
    private int date_year;
    private int date_month;
    private int date_day;
    private String week_day;
    
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName_local() {
        return name_local;
    }
    public void setName_local(String name_local) {
        this.name_local = name_local;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    @JsonFormat(pattern="dd/MM/yyyy")
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public int getDate_year() {
        return date_year;
    }
    public void setDate_year(int date_year) {
        this.date_year = date_year;
    }
    public int getDate_month() {
        return date_month;
    }
    public void setDate_month(int date_month) {
        this.date_month = date_month;
    }
    public int getDate_day() {
        return date_day;
    }
    public void setDate_day(int date_day) {
        this.date_day = date_day;
    }
    public String getWeek_day() {
        return week_day;
    }
    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }

}
