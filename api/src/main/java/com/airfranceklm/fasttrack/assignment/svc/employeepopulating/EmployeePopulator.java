package com.airfranceklm.fasttrack.assignment.svc.employeepopulating;

import com.airfranceklm.fasttrack.assignment.lib.repos.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeePopulator {
    private final @NonNull EmployeeRepository employeeRepository;

    @PostConstruct
    void populateEmployees() {
        employeeRepository.create("klm000001", "Paul", null);
        employeeRepository.create("klm000002", "John", null);
        employeeRepository.create("klm000003", "George", null);
        employeeRepository.create("klm000004", "Ringo", null);
    }
}
