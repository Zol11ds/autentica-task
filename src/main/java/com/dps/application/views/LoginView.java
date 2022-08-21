// [lv] Pieslēgšanās loga izskata konfigurācija

package com.dps.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("login") // tiek norādīts maršruts
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm login = new LoginForm(); // Tiek izveidota LoginForm() instance, kura eksistē Vaadin satvarā

    // Pieslēgšanās loga konfigurācija
    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(new H1("Datortehnikas pieprasīšanas sistēma"), login, new Span("Username: user"), new Span("Password: userpass"));
    }

    // Pieslēgšanās pārbaude
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
                    login.setError(true);
                }
    }
}
