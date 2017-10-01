package andrii.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authentication) throws Exception {

        authentication.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT email as username, password, enabled FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT u.email as username, ur.authority " +
                        "FROM user u, user_role ur " +
                        "WHERE u.email = ? AND " +
                        "u.id = ur.user_id")
                .passwordEncoder(passwordEncoder());
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/",
                        "/home",
                        "/authorize",
                        "/signUp",
                        "/currentUser").permitAll()
                .antMatchers("/logout",
                        "/chat/id",
                        "/chats",
                        "/messages/{chatId}",
                        "/message/last",
                        "/message",
                        "/unreadMessages/count/{chatId}",
                        "/friend-requests/**",
                        "/friends",
                        "/friends/remove",
                        "/friend/**",
                        "/friendship/status",
                        "/currentUser/profile",
                        "/user/{userId}",
                        "/search",
                        "/user/information/update").hasAnyAuthority("USER", "ADMIN")
                .and()
                .csrf().disable()
                .formLogin().loginPage("/login")
                .usernameParameter("username").passwordParameter("password");
    }
}
