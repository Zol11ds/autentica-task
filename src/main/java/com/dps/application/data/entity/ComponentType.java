// [lv] Komponentes veida klase
// Lielā mērā tikai tiek norādīts statusa nosaukums

package com.dps.application.data.entity;

import com.dps.application.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class ComponentType extends AbstractEntity {
    private String name;

    public ComponentType() {

    }

    public ComponentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
