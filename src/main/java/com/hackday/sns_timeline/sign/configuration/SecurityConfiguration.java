package com.hackday.sns_timeline.sign.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.hackday.sns_timeline.sign.service.SignService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final SignService signService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.authorizeRequests()
			.antMatchers("/timeLine/**","/content/**", "/subscribe/**","/member/search/**", "/profile/**").hasRole("MEMBER")
			.antMatchers("/", "/sign/**").anonymous()
			.anyRequest().permitAll()

			.and()
			.formLogin()
			.loginPage("/")
			.loginProcessingUrl("/sign/in")
			.defaultSuccessUrl("/sign/in", true)
			.usernameParameter("email")
			.failureUrl("/sign/fail")
			.permitAll()

			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/sign/out"))
			.logoutSuccessUrl("/")
			.invalidateHttpSession(true);
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(
			"/resources/**",
			"/v2/api-docs",
			"/configuration/ui",
			"/swagger-resources/**",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(signService).passwordEncoder(passwordEncoder());
	}
}
