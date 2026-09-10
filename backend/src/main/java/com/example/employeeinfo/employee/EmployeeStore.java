package com.example.employeeinfo.employee;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory employee storage. Data lives only while the app is running -
 * replace with a JPA repository + database later.
 */
@Repository
public class EmployeeStore {

    private final Map<String, Employee> employees = new ConcurrentHashMap<>();

    public List<Employee> findAll() {
        return employees.values().stream()
                .sorted((a, b) -> a.lastName().compareToIgnoreCase(b.lastName()))
                .toList();
    }

    public Employee add(EmployeeRequest request) {
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee(
                id,
                request.firstName().trim(),
                request.lastName().trim(),
                request.email().trim(),
                request.department().trim(),
                request.jobTitle().trim()
        );
        employees.put(id, employee);
        return employee;
    }
}
