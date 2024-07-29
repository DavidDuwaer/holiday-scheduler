package com.airfranceklm.fasttrack.assignment.lib.repos;

import com.airfranceklm.fasttrack.assignment.lib.jpaentities.Holiday;
import org.springframework.data.repository.CrudRepository;

public interface HolidaySpringDataRepository extends CrudRepository<Holiday, Long> {}