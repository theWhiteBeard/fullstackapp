package com.example.employeeinfo.employee;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmployeeStoreTest {

    private final EmployeeStore store = new EmployeeStore();

    @Test
    void addsAndReturnsEmployee() {
        Employee saved = store.add(new EmployeeRequest(
                "Jane", "Doe", "jane@example.com", "Engineering", "Developer"));

        assertNotNull(saved.id());
        assertEquals("Jane", saved.firstName());
        assertEquals(List.of(saved), store.findAll());
    }

    @Test
    void findAllSortsByLastName() {
        store.add(new EmployeeRequest("Ann", "Young", "a@x.com", "Sales", "Rep"));
        store.add(new EmployeeRequest("Bob", "Adams", "b@x.com", "Sales", "Rep"));

        List<String> lastNames = store.findAll().stream().map(Employee::lastName).toList();
        assertEquals(List.of("Adams", "Young"), lastNames);
    }

    @Test
    void trimsWhitespace() {
        Employee saved = store.add(new EmployeeRequest(
                "  Jane  ", "  Doe  ", "  jane@example.com  ", "  Eng  ", "  Dev  "));

        assertEquals("Jane", saved.firstName());
        assertEquals("jane@example.com", saved.email());
    }
}
