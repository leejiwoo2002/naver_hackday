package com.hackday.sns_timeline.common;

public final class CommonFunction {

	static public int getStartPageNumber(int page){
		return (int) Math.floor(page/10)*10 + 1;
	}

	static public int getLastPageNumber(int start, int totalPage){
		return start + 9 < totalPage ? start + 9 : totalPage;
	}
}
