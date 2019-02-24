package pl.opensource.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	  	@Override
	    protected void configure(HttpSecurity http) throws Exception {	  		
	        http
	        .formLogin().loginPage("/login").permitAll().and()
	        .csrf().disable()
	        .authorizeRequests()
	            .antMatchers("/register","/actuator/info","/actuator/health").permitAll()
	            .anyRequest().authenticated() 
	            .and()
	        .logout()
               .logoutUrl("/logmeout")
                   .logoutSuccessUrl("/login")
                   .permitAll();
	    }    
	    
	    @Bean
		public UserDetailsService myUserDetailsService() {
			return new MyUserDetailsService();
		} 
	    
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin@123").roles("admin");
	    }
}