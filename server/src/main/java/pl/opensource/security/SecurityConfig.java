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
//		.cors()
//		.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//		.and()
		.httpBasic()
		.and()
    	.csrf().disable()
//		 .csrf()
//         .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
		.authorizeRequests()
				.antMatchers("/**", "/index.html", "/resources/**", "/actuator/info", "/actuator/health", "/login", "/user/register", "/advertisement", "/advertisement/sport", "/advertisement/{id}", "/advertisement/{sport}/{city}", "/advertisement/getParticipantCount/{id}").permitAll()
				.anyRequest().authenticated()
				.and()
		.formLogin()
        .loginPage("/login")
        .and()
		.logout()
		.logoutUrl("/logmeout")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID");
	}
	
//	@Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("OPTIONS");
//        config.addAllowedMethod("GET");
//        config.addAllowedMethod("POST");
//        config.addAllowedMethod("PUT");
//        config.addAllowedMethod("DELETE");
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
	
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
