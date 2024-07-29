package com.airfranceklm.fasttrack.assignment.svc.holiday.read;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/holidays")
@RequiredArgsConstructor
class Controller {
    private final @NonNull DAO dao;

    @GetMapping
    public @NonNull List<Holiday> get() {
        return dao.getHolidays();
    }
}
