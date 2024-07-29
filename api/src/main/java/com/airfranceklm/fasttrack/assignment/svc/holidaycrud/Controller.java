package com.airfranceklm.fasttrack.assignment.svc.holidaycrud;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/holidays")
public class Controller {
    @GetMapping
    public ResponseEntity<List<Holiday>> getHolidays() {
        throw new UnsupportedOperationException();
    }
}
