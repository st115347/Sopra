package de.uni.hohenheim.sopra.projekt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

/**
 * Created by bumiller on 06.06.2016.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private DataSource dataSource;

  /**
   * Konfiguration der Sicherheitsrechte in der Anwendung. Die Reihenfolge des Aufbaus spielt hier eine wichtige Rolle!
   *
   * @param http the HttpSecurity context of the application
   * @throws Exception some exception.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        //Zuerst müssen Seiten konfiguriert werden, welche ohne Zugriffsrechte aufgerufen werden dürfen
        //(falls vorhanden)
        .antMatchers("/login").permitAll()
        .antMatchers("/register").permitAll()
        //Für Address-Unterbereiche, welche nur mit speziellen Rechten betreten werden können, kann dies explizit
        //angegeben werfen. (Hier: alles hinter /admin/... , z.b. /admin/home/ benötigt admin Rechte.
        .antMatchers("/admin/**").hasRole("ADMIN")
        //Jeder bisher nicht erwähnte Aufruf in der Anwendung muss authentifiziert sein. Bisher konfigurierte Ausnahmen
        //werden nicht überschrieben!
        .anyRequest().fullyAuthenticated().and()
        //Konfiguration des Logins. Unter welcher Addresse ist die Login Seite zu finden etc.
        .formLogin().loginPage("/login").failureUrl("/login?error").permitAll().and()
        //jeder darf sich ausloggen
        .logout().permitAll().and()
        //Sicherheit für /console ausgeschaltet
        .csrf().ignoringAntMatchers("/console/**").disable();
    //header security ausgeschalten, um h2 console zu erlauben
    http.headers().frameOptions().disable();
  }

  /**
   * Configuration of the Security context. Activate JDBC Management.
   *
   * @param auth autowire the spring component.
   * @throws Exception some exception.
   */
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().withDefaultSchema().dataSource(dataSource).passwordEncoder(passwordEncoder());
  }

  /**
   * Um Nutzer mit dem implementierten Service von Spring zu verwalten und authentifizeren, muss dieser Service
   * als Bean erreichbar sein. Dies wird hier konfiguriert.
   *
   * @param dataSource the datasource
   * @return JdbcUserDetailsManager
   */
  @Bean
  @Autowired
  public JdbcUserDetailsManager jdbcUserDetailsManager(@NotNull DataSource dataSource, AuthenticationManager auth) {
    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
    jdbcUserDetailsManager.setDataSource(dataSource);
    jdbcUserDetailsManager.setAuthenticationManager(auth);

    return jdbcUserDetailsManager;
  }

  /**
   * Die passwordEncoder bean. Die Implementation ist von Spring Security vorgegeben.
   *
   * @return The BCryptPasswordEncoder bean.
   */
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
