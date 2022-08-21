// [lv] Pieprasījuma Spring Data JPA repozitorija, ar kuru iespējams apstrādāt vaicājumu
// Dati tiek iegūti ar generator/DataGenerator klasi

package com.dps.application.data.repository;

import com.dps.application.data.entity.TechComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// Šajā konfigurācijā ar filtru ir iespējams apstrādāt komponentes veida un pieprasījuma statusu vaicājumus
// (norādot filtrā vēlamo nosaukumu)
public interface ComponentRepository extends JpaRepository<TechComponent, UUID> {

    @Query("select c from TechComponent c " +
        "where lower(c.justification) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.componentType.name) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(c.status.name) like lower(concat('%', :searchTerm, '%'))")
    List<TechComponent> search(@Param("searchTerm") String searchTerm);
}
