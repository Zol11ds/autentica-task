// [lv] Galvenā klase, kas atbilst datortehnikas komponentes pieprasījumam
// Objekti tiek raksturoti ar komponentes veidu, ražotāju, skaitu, pamatojumu, iesniegšanas datumu un statusu

package com.dps.application.data.entity;

import com.dps.application.data.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class TechComponent extends AbstractEntity {

    @NotNull
    @ManyToOne
    private ComponentType componentType;

    @NotNull
    @ManyToOne
    private Manufacturer manufacturer;

    @NotNull
    @NumberFormat
    private Integer number = 1;

    @NotEmpty
    private String justification = "";

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appDate;

    @NotNull
    @ManyToOne
    private Status status;


    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Integer getNumber() { return number; }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public LocalDateTime getAppDate() {return appDate;}

    public void setAppDate(LocalDateTime appDate) { if (appDate != null) this.appDate = appDate;}
}
