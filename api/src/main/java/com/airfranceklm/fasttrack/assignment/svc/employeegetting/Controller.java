package com.airfranceklm.fasttrack.assignment.svc.employeegetting;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Employee;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class Controller {
    private final @NonNull DAO dao;

    @GetMapping("/employees")
    public @NonNull List<Employee> get() {
        return dao.getEmployees();
    }
}
