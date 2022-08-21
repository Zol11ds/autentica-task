// [lv] Galvenais datortehnikas komponenšu skats, kur lietotājs var sameklēt, pievienot,
// rediģēt vai dzēst pieprasījumus

package com.dps.application.views.list;

import com.dps.application.data.entity.TechComponent;
import com.dps.application.data.service.CmService;
import com.dps.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.RolesAllowed;
import java.time.format.DateTimeFormatter;

@org.springframework.stereotype.Component
@Scope("prototype")
@PageTitle("DPS")
@RolesAllowed("USER")
@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {
    Grid<TechComponent> grid = new Grid<>(TechComponent.class); // elementu "režgis", kurā atradīsies elementi
    TextField filterText = new TextField(); // Teksta lauks, kur lietotājs varēs meklēt pieprasījumus pēc atbilstošiem parametriem
    TechCompForm form; // Tiek norādīts "veidlapas" elements, balstoties uz TechCompForm klasi
    private CmService service; // Pieprasījumu apstrāde ar servisu

    public ListView(CmService service) {
        this.service = service; // Pieprasījumu apstrādes serviss, kas atrodas service/CmService
        addClassName("list-view");
        setSizeFull(); // Skats tiek izvietots pa visu lapu

        configureGrid(); // Apstrādā elementu "režģi"
        configureForm(); // Apstrādā "veidlapu"

        // Pievieno norādītos elementus skatam
        add(
          getToolbar(),
          getContent()      
        );

        updateList(); // Atjaunina sarakstu
        closeEditor(); // Sākotnēji aizver "veidlapu", kad sākotnēji ienāk mājaslapā
    }

    // Paslēpj "veidlapu"
    private void closeEditor() {
        form.setComponent(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    // Atjaunina sarakstu atbilstoši filtrā norādītajam tekstam
    private void updateList() {
        grid.setItems(service.findAllComponents(filterText.getValue()));
    }

    // Apvieno režgi un veidlapu, norāda formatēšanas stilu, attiecības
    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    // Veidlapas konfigurācija
    private void configureForm() {
        // Iniciē veidlapas objektu, kurā tiek norāditi ražotāji, statusu un komponenšu veidi, izmantojot apstrādes servisu
        form = new TechCompForm(service.findAllManufacturers(), service.findAllStatuses(), service.findAllCompTypes());
        form.setWidth("25em");

        // Piesaista veidlapas notikumus ar apstrādi galvenajā skatā
        form.addListener(TechCompForm.SaveEvent.class, this::saveComponent);
        form.addListener(TechCompForm.DeleteEvent.class, this::deleteComponent);
        form.addListener(TechCompForm.CloseEvent.class, e -> closeEditor());
    }

    // Saglabā repozitorijā jauno pieprasījumu un atjaunina tabulu + paslēpj veidlapu
    private void saveComponent(TechCompForm.SaveEvent event){
        service.saveComponent(event.getContact());
        updateList();
        closeEditor();
    }

    // Izdzēš repozitorijā norādīto pieprasījumu un atjaunina tabulu + paslēpj veidlapu
    private void deleteComponent(TechCompForm.DeleteEvent event){
        service.deleteComponent(event.getContact());
        updateList();
        closeEditor();
    }

    // Rīkjoslas konfigurācija
    private Component getToolbar() {
        filterText.setPlaceholder("Filtrēt...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); // Filtrēšana aktivizējas tikai pēc neliela laika, kad lietotājs beidz ievadi
        filterText.addValueChangeListener(e -> updateList()); // Atjaunina tabulas elementus atbilstoši filtrā norāditājam tekstam

        // Poga, kas ļauj pievienot jaunu pieprasījumu sarakstā
        Button addComponentButton = new Button("Pievienot pieprasījumu");
        addComponentButton.addClickListener(e -> addComponent());

        // Horizontāli tiek savienots teksta logs un poga
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addComponentButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    // Jauna pieprasījuma pievienošana
    private void addComponent() {
        grid.asSingleSelect().clear();
        editComponent(new TechComponent());
    }

    // "Režģa" apstrāde, lai atbilstoši apstrādātu un norādītu vajadzīgo info
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(techcomp -> techcomp.getComponentType().getName()).setHeader("Tehnikas veids");
        grid.addColumn(techcomp -> techcomp.getManufacturer().getName()).setHeader("Ražotājs");
        grid.addColumn(techcomp -> techcomp.getNumber()).setHeader("Skaits");
        grid.addColumn(techcomp -> techcomp.getJustification()).setHeader("Pamatojums");
        grid.addColumn(techcomp -> techcomp.getAppDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .setHeader("Datums").setKey("date").setSortable(true);
        grid.addColumn(techcomp -> techcomp.getStatus().getName()).setHeader("Statuss").setSortable(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true)); // Kolonnas lielums pielāgojas atbilstoši saturam

        // Ja tiek uzspiests uz kādu no tabulas rindām, tad atbilstoši tiek pārādīta veidlapa ar atbilstošiem datiem
        grid.asSingleSelect().addValueChangeListener(e -> editComponent(e.getValue()));
    }

    // Tiek iespējota rediģēšnas veidlapa, kā arī veidlapā tiek norādīta aktīvais (izvēlētais) pieprasījums
    private void editComponent(TechComponent component) {
        // Ja tiek atkārtoti uzspiests uz izvēlētās rindas, tad rediģēšanas veidlapa tiek paslēpta
        if (component == null) {
            closeEditor();
        }
        else {
            form.setComponent(component);
            form.setVisible(true);
            addClassName("editing");
        }
    }

}
