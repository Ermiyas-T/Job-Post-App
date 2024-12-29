// JobApplication.java
package com.jobpost;

public class JobApplication {
    private String email;
    private String title;
    private String description;
    private String location;
    private String type;
    private String salary;
    private int phone;
    private String address;
    private int age;

    public JobApplication(String email, String title, String description, String location, String type, String salary, int phone, String address, int age) {
        this.email = email;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
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

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }
}
