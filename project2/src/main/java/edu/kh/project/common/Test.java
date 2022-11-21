package edu.kh.project.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws Exception{
		
		// Date 				: 날짜용 객체
		// Calendar				: Date 업그레이드 객체
		// SimpleDateFormat		: 날짜를 원하는 형태의 문자열로 변환
		
		Date a = new Date();	// 현재 시간
		// Date b = new Date(0);	// 1970년 1월 1일 09시 0분 0초	== 자바에서 시간계산을 할 때 기준으로 잡는 시간	
		// Date b = new Date(1000);	// new Date(ms) : 기준 시간 + ms 만큼 지난 시간
		Date b = new Date(1669087702278L);	
		Calendar cal = Calendar.getInstance();
		
		// cal.add(단위, 추가할 값);
		// 단위 == 년, 월, 일, 시, 분, 초
		cal.add(cal.DATE, 1); 	// 날짜에 1 추가
		
		// SimpleDateFormat을 이용해서 cal 날짜 중 시,분,초를 0:0:0 바꿈
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date temp = new Date(cal.getTimeInMillis());
		// 하루 증가한 내일 날짜의 ms 값을 이용해서 Date 객체 생성
		
		System.out.println(a);
		System.out.println(temp);
		// System.out.println(sdf.format(temp));
		Date c = sdf.parse(sdf.format(temp));
		System.out.println(c);
		
		// 내일 자정 ms -  현재시간 ms
		long diff = c.getTime() - a.getTime();
		System.out.println(diff / 1000 -1);
	}
}
