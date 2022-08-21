// [lv] "Veidlapas" elements, kurā var rediģēt izvēlēto pieprasījumu vai konfigurēt jaunu pieprasījumu

package com.dps.application.views.list;

import com.dps.application.data.entity.ComponentType;
import com.dps.application.data.entity.TechComponent;
import com.dps.application.data.entity.Manufacturer;
import com.dps.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;
import java.util.List;

public class TechCompForm extends FormLayout {
    // Datu piesaiste starp veidlapu un galveno skatu jeb tajā esošo tabulu
    Binder<TechComponent> binder = new BeanValidationBinder<>(TechComponent.class);

    // Veidlapā esošo elementu norādīšana
    ComboBox<ComponentType> componentType = new ComboBox<>("Tehnikas veids");
    ComboBox<Manufacturer> manufacturer = new ComboBox<>("Ražotājs");
    IntegerField number = new IntegerField();
    TextField justification = new TextField("Pamatojums");
    ComboBox<Status> status =  new ComboBox<>("Statuss");

    Button save = new Button("Saglabāt");
    Button delete = new Button("Dzēst");
    Button cancel = new Button("Atcelt");
    private TechComponent component;

    // Elementu konfigurācija, kurā tiek norādīts, kādi ir ražotāja, statusu un komponenšu veidi,
    // kā arī elementu ievietošana veidlapā
    public TechCompForm(List<Manufacturer> manufacturers, List<Status> statuses, List<ComponentType> componentTypes) {
        addClassName("contact-form");
        binder.bindInstanceFields(this); // sasaista vienādi nosauktus elementus starp TechComponent un TechCompForm

        // Komponentes skaita elementa konfigurācija
        number.setLabel("Skaits");
        number.setHelperText("Maksimāli 15");
        number.setMin(1);
        number.setMax(15);
        number.setValue(1);
        number.setHasControls(true);

        componentType.setItems(componentTypes);
        componentType.setItemLabelGenerator(ComponentType::getName);

        manufacturer.setItems(manufacturers);
        manufacturer.setItemLabelGenerator(Manufacturer::getName);

        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(
                componentType,
                manufacturer,
                number,
                justification,
                status,
                createButtonsLayout() // Pogas un to konfigurācijas tiek iegūtas funkcijas veidā
        );
    }

    // Piesaista noteiktā objekta vērtības uz atbilstošajiem lietotāja interfeisa elementiem
    public void setComponent(TechComponent component){
        this.component = component;
        binder.readBean(component);
    }

    // Pogu izkārtojuma un atbilstošo tastatūras īsinājumtaistiņu komnfigurācija
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // Pogām tiek piesaistītas funkcijas/notikumi
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, component)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    // Tiek saglabāti dati uz piesaistītā objekta
    private void validateAndSave() {
        try{
            binder.writeBean(component);
            fireEvent(new SaveEvent(this, component));
        }
        catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class TechCompFormEvent extends ComponentEvent<TechCompForm> {
        private TechComponent component;

        protected TechCompFormEvent(TechCompForm source, TechComponent component) {
            super(source, false);
            this.component = component;
        }

        public TechComponent getContact() {
            return component;
        }
    }

    public static class SaveEvent extends TechCompFormEvent {
        SaveEvent(TechCompForm source, TechComponent component) {
            super(source, component);
            component.setAppDate(LocalDateTime.now());
        }
    }

    public static class DeleteEvent extends TechCompFormEvent {
        DeleteEvent(TechCompForm source, TechComponent component) {
            super(source, component);
        }

    }

    public static class CloseEvent extends TechCompFormEvent {
        CloseEvent(TechCompForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
