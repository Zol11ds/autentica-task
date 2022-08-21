// [lv] Statusa klase, kas atbilst pieprasījuma statusam
// Lielā mērā tikai tiek norādīts statusa nosaukums

package com.dps.application.data.entity;

import com.dps.application.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Status extends AbstractEntity {
    private String name;

    public Status() {

    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
