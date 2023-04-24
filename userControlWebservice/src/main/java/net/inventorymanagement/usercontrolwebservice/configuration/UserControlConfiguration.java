package net.inventorymanagement.usercontrolwebservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

/**
 * User configuration rest configuration, provides ldap-authentication-instance and -interface.
 */

@Configuration
@EnableWebSecurity
public class UserControlConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    // ldap-instance in production, otherwise an in-memory authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (isProduction()) {
            ActiveDirectoryLdapAuthenticationProvider adProvider =
                new ActiveDirectoryLdapAuthenticationProvider("YOUR.DOMAIN.net", "ldap://IP.OF.YOUR.LDAP:389");
            adProvider.setConvertSubErrorCodesToExceptions(true);
            adProvider.setUseAuthenticationRequestCredentials(true);
            auth.authenticationProvider(adProvider);
        } else {
            auth
                .inMemoryAuthentication()
                .withUser("Super Admin")
                .password(passwordEncoder().encode("password"))
                .authorities("ROLE_USER");
        }

    }

    // authentication-interface
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private boolean isProduction() {
        String[] environments = this.environment.getActiveProfiles();
        for (String env : environments){
            if (env.equalsIgnoreCase("prod")) {
                return true;
            }
        }
        return false;
    }
}
