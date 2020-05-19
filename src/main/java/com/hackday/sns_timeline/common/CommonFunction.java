package com.hackday.sns_timeline.common;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class CommonFunction {

	static public int getStartPageNumber(int page){
		return (int) Math.floor(page/10)*10 + 1;
	}

	static public int getLastPageNumber(int start, int totalPage){
		return start + 9 < totalPage ? start + 9 : totalPage;
	}

	static public Date getCurrentDate(){
		LocalDateTime currentDateTime = LocalDateTime.now();
		return java.sql.Timestamp.valueOf(currentDateTime);
	}

	static public String encodingPassword(String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}
}
