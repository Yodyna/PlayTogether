package pl.opensource.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.httpBasic().and()
		.csrf().disable()
		.authorizeRequests()
				.antMatchers("/register", "/actuator/info", "/actuator/health","/login", "/advertisement/","/advertisement").permitAll()
				.anyRequest().authenticated()
				.and()
		.logout()
		.logoutUrl("/logmeout")
			.logoutSuccessUrl("/login").permitAll();
	}

	@Bean
	public UserDetailsService myUserDetailsService() {
		return new MyUserDetailsService();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}