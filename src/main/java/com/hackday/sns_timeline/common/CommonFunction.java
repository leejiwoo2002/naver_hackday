package com.hackday.sns_timeline.common;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonFunction {

	public static int getStartPageNumber(int page){
		return (int) Math.floor(page/10)*10 + 1;
	}

	public static int getLastPageNumber(int start, int totalPage){
		return start + 9 < totalPage ? start + 9 : totalPage;
	}

	public static Date getCurrentDate(){
		LocalDateTime currentDateTime = LocalDateTime.now();
		return java.sql.Timestamp.valueOf(currentDateTime);
	}

	public static String encodingPassword(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
}
