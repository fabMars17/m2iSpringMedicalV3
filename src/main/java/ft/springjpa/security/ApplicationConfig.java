package ft.springjpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;
import javax.ws.rs.Encoded;
import java.beans.Encoder;

@Configuration
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure (AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        /*auth.jdbcAuthentication()
                        .dataSource(dataSource)
                        .usersByUsernameQuery("select email,password , 1 as enabled "
                                +"from user "
                                +"where email= ?")
                        .authoritiesByUsernameQuery(
                                "select email,password,roles "
                                +"from user "
                                +"where email= ?")
                        .passwordEncoder(passwordEncoder());*/

        //System.out.println(passwordEncoder().encode("1234"));
        //auth.inMemoryAuthentication().withUser("admin").password("$2a$10$rjHaRHnUvUdgByDe0.LHD.DCV39xfByRaVGkAQVDM6yIlPfyhktKS").roles("ADMIN");
    }

    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true);
        //http.formLogin().defaultSuccessUrl("/patient/list");
        http.authorizeRequests().antMatchers("/login", "/css/**").permitAll();
        http.authorizeRequests().antMatchers("/add/**", "/edit/**").hasRole("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
