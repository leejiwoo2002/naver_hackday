package com.hackday.sns_timeline.configuration;

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

import com.hackday.sns_timeline.service.SignService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final SignService signService;

	// 패스워드 인코더 빈 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 권한 매니저 빈 등록
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable() // 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
			.authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
			.antMatchers("/timeLine/**","/content/**", "/subscribe/**", "/").hasRole("MEMBER")
			.antMatchers("/", "/sign/**").anonymous() // 가입 및 인증 주소는 누구나 접근가능
			.antMatchers("/resources/**").permitAll()
			.anyRequest().hasRole("MEMBER") // 그외 나머지 요청은 모두 인증된 회원만 접근 가능

			.and()
			.formLogin() // 로그인 폼 설정
			.loginPage("/sign/in") // 로그인 URL
			.defaultSuccessUrl("/sign/in", true) // 로그인 성공 시 URL
			.usernameParameter("email") // 로그인 폼에서 username 의 파라미터 커스텀
			.permitAll() // 권한 설정

			.and()
			.logout() // 로그아웃 설정
			.logoutRequestMatcher(new AntPathRequestMatcher("/sign/out")) // 로그아웃 URL
			.logoutSuccessUrl("/") // 로그아웃 성공 시 URL
			.invalidateHttpSession(true); // 세션 초기화
	}

	@Override
	public void configure(WebSecurity web) {
		// 시큐리티 적용 무시 URL
		web.ignoring().antMatchers("/resources/**");
	}

	// 로그인 설정
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// userDetailService 인터페이스를 상속받은 signService 의 loadUserByUsername 로직 사용
		// 패스워드 인코더 설정
		auth.userDetailsService(signService).passwordEncoder(passwordEncoder());
	}

}
