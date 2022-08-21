// Galvenās lapas izkārtojums, kas tiek norādīts ListView skatā

package com.dps.application.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainLayout extends AppLayout {

    public MainLayout(){
        createHeader();
    }

    private void createHeader() {
        // Izveidota galvene
        H1 logo = new H1("Datortehnikas pieprasīšanas sistēma");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(logo);

        // Galvenes konfigurācija
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
}
