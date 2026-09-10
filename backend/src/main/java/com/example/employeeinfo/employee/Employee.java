package com.example.employeeinfo.employee;

public record Employee(
        String id,
        String firstName,
        String lastName,
        String email,
        String department,
        String jobTitle
) {
}
