package com.jobpost;

public class Job {
    private String title;
    private String description;
    private String location;
    private String type;
    private String salary;

    public Job(String title, String description, String location, String type, String salary) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.salary = salary;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getSalary() {
        return salary;
    }
}
