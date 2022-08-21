// [lv] Pieslēgšanās loga konfigurācija, izmantojot Spring Security

package com.dps.application.security;

import com.dps.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class, "/logout");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**");
        super.configure(web);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        // [lv] Neizmantot reālā situācijā !!! [en] Not for commercial use !!!
        // Var izmantot papildus bibliotēkas, kas ir drošākas/labākas alternatīvas
        return new InMemoryUserDetailsManager(User.withUsername("user").password("{noop}userpass").roles("USER").build());
    }
}
