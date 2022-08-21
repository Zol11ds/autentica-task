// [lv] Ražotāja klase, kas atbilst komponentes ražotājam
// Lielā mērā tikai tiek norādīts statusa nosaukums

package com.dps.application.data.entity;

import com.dps.application.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Manufacturer extends AbstractEntity {
    private String name;

    public Manufacturer() {

    }

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
