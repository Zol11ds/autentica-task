// [lv] Statusu Spring Data JPA repozitorija
// Dati tiek iegūti ar generator/DataGenerator klasi

package com.dps.application.data.repository;

import com.dps.application.data.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatusRepository extends JpaRepository<Status, UUID> {

}
