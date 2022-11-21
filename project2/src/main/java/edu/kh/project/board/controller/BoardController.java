package edu.kh.project.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.board.model.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	
	// 특정 게시판 목록 조회
	// /board/1
	// /board/2
	// /board/3
	// /board/4
	// -> @PathVariable 사용
	//    URL 경로에 있는 값을 파라미터(변수)로 사용할 수 있게하는 어노테이션
	//    + 자동으로 request scope로 등록되어 EL 구문으로 jsp에서 출력도 가능
	
	// 요청주소?K=V&K=V&K=V&K=V..... (queryString, 쿼리스트링)
	// -> 요청주소에 값을 담아서 전달할 때 사용하는 문자열
	
	@GetMapping("/board/{boardCode}")
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
			Model model,
			@RequestParam(value="cp", required=false, defaultValue="1") int cp
			) {
		
		// Model:값 전달용 객체
		// Model.addAttribute("K":"V") : request scope에 세팅
		// 								 -> forward시 유지됨
		
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		model.addAttribute("map", map);
		
		return "board/boardList";
	}
	
	// 게시글 상세 조회
	@GetMapping("/board/{boardCode}/{boardNo}")
	public String boardDetail(
			@PathVariable("boardNo") int boardNo,
			@PathVariable("boardCode") int boardCode,
			Model model, HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		
		// 게시글 상세조회 서비스 호출
		Board board = service.selectBoardDetail(boardNo);
		
		// 게시글 상세 조회 성공 시 조회 수 증가
		if(board != null) {		// 조회한 게시글이 존재한다면
			// + 조회수 증가(쿠키를 이용해서 해당 IP당 하루 한번)
			// 컴퓨터 1대당 게시글마다 1일 1번씩만 조회수 증가
			// -> 쿠키 이용
			
			// Cookie
			// - 사용되는 경로, 수명
			// -> 경로 지정 시
			//    해당 경로 또는 이하 요청을 보낼 때
			//    쿠키 파일을 서버로 같이 보냄
			
			// 쿠키에 "readBoardNo"를 얻어와
			// 현재 조회하려는 게시글 번호가 없으면
			// 조회 수 1 증가 후 쿠키에 게시글 번호 추가
			
			// 쿠키 얻어오기
			Cookie[] cookies = req.getCookies(); // getCookies() 존재하는 쿠키를 배열형태로 반환, 없으면 null
			
			// 쿠키 중 "readBoardNo"가 있는지 확인
			Cookie c = null;
			
			int result = 0;		// 조회 수 증가 service 호출 결과 저장
			
			if(cookies != null) {		// 쿠키가 있을 때 
				for(Cookie temp : cookies) {
					if(temp.getName().equals("readBoardNo")) {		// 쿠키 이름이 "readBoardNo" 라면
						c = temp;
						break;
					}	
				}
			}
			if(c == null) {				// 기존의 "readBoardNo" 쿠키가 없다면 
										// == 오늘 상세조회 한번도 안했다
				result = service.updateReadCount(boardNo);
				
				// boardNo 게시글을 상세조회 했음을 쿠키에 기록
				c = new Cookie("readBoardNo","|" + boardNo + "|");
												// |1500|
				// 톰캣 8.5 이상부터 쿠키의 값으로
				// ; , = (공백) 사용 불가
				
			} else {					// 기존의 "readBoardNo" 쿠키가 있다면
				// c.getValue() : "readBoardNo" 쿠키에 저장된 값 (|4000|)
				
				// 쿠키에 저장된 값 중 "|게시글번호|" 가 존재하는지 확인
				if(c.getValue().indexOf("|" + boardNo + "|") == -1) {		// 쿠키의 value에 boardNo가 존재하지 않는 경우 (오늘 처음 조회하는 게시글 번호)
					result = service.updateReadCount(boardNo);
					
					// 현재 조회한 게시글 번호를 쿠키의 값으로 추가
					c.setValue(c.getValue() + "|" + boardNo + "|");
				}			
			}

			if(result > 0) {	// 조회스 증가가 성공하면
				// 미리 가져온 게시글의 조회수 1증가하여 데이터 동기화
				board.setReadCount(board.getReadCount() + 1);
				
				// 쿠키 적용 경로, 수명 설정 후 클라이언트에게 전달
				c.setPath("/");		// localhost/(최상위 경로 /) 이하로 적용
				
				// 오늘 23시 59분 59초 까지 남은 시간을 초단위로 구하기
				
				// Date 				: 날짜용 객체
				// Calendar				: Date 업그레이드 객체
				// SimpleDateFormat		: 날짜를 원하는 형태의 문자열로 변환
				
				Date a = new Date();	// 현재 시간
				// Date b = new Date(0);	// 1970년 1월 1일 09시 0분 0초	== 자바에서 시간계산을 할 때 기준으로 잡는 시간	
				// Date b = new Date(1000);	// new Date(ms) : 기준 시간 + ms 만큼 지난 시간
				// Date b = new Date(1669087702278L);	
				Calendar cal = Calendar.getInstance();
				
				// cal.add(단위, 추가할 값);
				// 단위 == 년, 월, 일, 시, 분, 초
				cal.add(cal.DATE, 1); 	// 날짜에 1 추가
				
				// SimpleDateFormat을 이용해서 cal 날짜 중 시,분,초를 0:0:0 바꿈
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date temp = new Date(cal.getTimeInMillis());
				// 하루 증가한 내일 날짜의 ms 값을 이용해서 Date 객체 생성
				
				// System.out.println(sdf.format(temp));
				Date b = sdf.parse(sdf.format(temp));

				// 내일 자정 ms -  현재시간 ms
				long diff = b.getTime() - a.getTime();
				// System.out.println(diff / 1000);		// 23:59:59초까지 남은 시간
				
				c.setMaxAge((int)(diff / 1000));	
				resp.addCookie(c);	// 쿠키를 클라이언트에게 전달
			}
		}
		// + 좋아요 수, 좋아요 여부
		
		model.addAttribute("board", board);
		
		return "board/boardDetail";
	}
}
