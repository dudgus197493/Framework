package edu.kh.project.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.service.MemberService;
import edu.kh.project.member.model.vo.Member;



// 회원 관련 요청을 받는 컨트롤러
// Controller : 프레젠테이션 레이어
// 				웹 애플리케이션으로 전달 받은 클라이언트의 요청을
// 				알맞은 서비스로 연결하고
//				서비스에서 반환된 결과에 따라
//				알맞은 화면으로 응답하는 방법을 제어하는 역할

// Controller 어노테이션 : 컴파일러에게 현재 클래스가 Controller임을 알려줌
//						+ bean 등록 (Spring이 객체로 만들어서 관리)
@Controller

@SessionAttributes({"loginMember", "message"})
// @SessionAttributes({"loginMember", "test"}) : 여러개의 값을 지정하고 싶으면 배열 형식으로 작성
// -> Model에 추가된 속성 중 Key가 일치하는 속성을 session scope 속성으로 추가
public class MemberController {
	
	// * 공용으로 사용할 Service 객체 생성 *
	// @Autowired
	// bean scanning을 통해 bean으로 등록된 객체 중
	// 알맞은 객체를 DI(의존성 주입) 해주는 어노테이션
	
	// 자동 연결 규칙 : 타입이 같거나 상속 관계인 bean을 자동으로 DI
	
	// 같은 타입이 여러개 있다면 @Qualifier("name")을 사용하여 DI할 bean 명시

	
	@Autowired
	private MemberService service;
	
	// @RequestMapping : 클라이언트의 요청을  처리할 클래스/메서드를 지정하는 어노테이션
	// == Handler Mapping
	
	
	// **** 파라미터를 전달 받는 방법 ****
	// 1. HttpServletRequest를 이용하는 방법
	
	// 로그인 요청(POST)
//	@RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req) {
	
		String inputEmail = req.getParameter("inputEmail");
		
		String inputPw = req.getParameter("inputPw");
	
		System.out.println(inputEmail);
		System.out.println(inputPw);
		
	
		// * forward 방법   : prefix/suffix를 제외한 나머지 jsp 경로
		// * redirect 방법  : "redirect:요청주소"
		return "redirect:/";
	}
	
	// 2. @RequestParam 어노테이션 사용
	// - 메서드 매개변수에 전달받은 파라미터를 주입하는 어노테이션
	   // [속성]
	   // value : 전달 받은 input 태그의 name 속성값
	   
	   // required : 입력된 name 속성값 파라미터 필수 여부 지정(기본값 true)
	   // -> required = true인 파라미터가 존재하지 않는다면 400 Bad Request 에러 발생
	   // -> required = true인 파라미터가 null인 경우에도 400 Bad Request

	   // defaultValue : 파라미터 중 일치하는 name 속성 값이 없을 경우에 대입할 값 지정.
	   // -> required = false인 경우 사용
	
	   // * @RequestParam 생략 하기 *
	   // 조건 : 매개변수 이름 == input의 name 속성 값
		/* @RequestMapping(value="/member/login", method = RequestMethod.POST) */
	public String login(@RequestParam("inputEmail") String email, @RequestParam(value = "inputPw2", required = false, defaultValue="1234") String pw,
			String inputPw) {
		System.out.println(email);
		System.out.println(pw);
		System.out.println(inputPw);
		return "redirect:/";
	}
	
	// @RequestParam 생략을 이용해서 짧게 코드 작성 가능
	// @RequestMapping(value="/member/login", method = RequestMethod.POST)
	public String login(String email, String inputPw) {
		System.out.println(email);
		System.out.println(inputPw);
		
		return "redirect:/";
	}
	
	// == @RequestMapping(value="/member/login", method= RequestMethod.POST)
	// @PostMapping("/member/login")// POST 방식의 /member/login 요청을 연결
	// @GetMapping("/member/login")	// Get  방식의 /member/login 요청을 연결

	// 3. @ModelAttribute 어노테이션 이용
	
	// [작성법]
	// @ModelAttribute VO타입 매개변수명
	// -> 파라미터의 name속성 값이
	//	  지정된 VO의 필드명과 같다면 
	//    해당 VO객체의 필드에 파라미터를 세팅
	
	// [조건]
	// 1. name 속성값과 필드명이 같아야함.
	// 2. VO에 반드시 기본 생성자가 존재
	// 3. VO에 반드시 setter가 존재해야함
	
	// * @ModelAttribute 어노테이션 생략 가능
	// == 커맨드 객체
	
	// * 참고 *
	// Controller 메서드 매개변수에 객체를 작성하면
	// 자동으로 생성되거나 얻어올 수 있는 이유
	// -> Spring Container에서 Argument Resolver 제공
	//    유연하게 처리함
	
	@PostMapping("/member/login")
	public String login(/* @ModelAttribute */ Member inputMember,
						Model model, @RequestParam(value="saveId", required = false) String saveId,
						HttpServletResponse resp, // 쿠키 전달용
						RedirectAttributes ra,
						@RequestHeader(value="referer") String referer // 요청 이전 주소
						) { 
		// Model : 데이터 전달용 객체
		// - 데이터를 Map 형식으로 저장하여 전달하는 객체
		// - request scope가 기본값
		//   + @SessionAttributes 어노테이션과 함께 작성 시 session scope로 변환 가능		
		
		// RedirectAttributes
		// - 리다이렉트 시 값을 전달하는 용도의 객체
		// - 응답 전 : request scope
		// - redirect 중 : session scope
		// - 응답 후 : request scope
		
		// Servlet 프로젝트
		// Service 객체 생성
		// try ~ catch내부에 코드 작성
		
		// Spring 프로젝트
		
		// 서비스 호출 후 결과 반환 받기
		Member loginMember = service.login(inputMember);
		
		String path = ""; 		// 리다이렉트 경로를 저장할 변수 
		
		if(loginMember != null) { // 로그인 성공
			path = "http://localhost:8080/"; // 로그인 성공 시 메인 페이지 리다이렉트
			
			// 로그인 성공 시 loginMember를 세션에 추가
			
			// addAttribute("K", V) == req.setAttribute("K", V)
//			ra.addFlashAttribute("loginMember", loginMember);
			model.addAttribute("loginMember", loginMember);
			// -> request scope 상태
			
			// @SessionAttributes("loginMember")를 클래스 위에 추가
			// -> session scope로 반환
			
			// ************************************************
			// 쿠키 생성
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
			
			// 쿠키 유지 시간 설정
			if(saveId != null) {   // 아이디 저장 체크 O
				
				// 1년동안 쿠키 유지
				cookie.setMaxAge(60 * 60 * 24 * 365);
				
			} else {			   // 아이디 저장 체크 X
				
				// 0초 동안 쿠키 유지 -> 생성과 동시에 삭제
				// --> 클라이언트쪽의 기존 쿠키 파일을 삭제
				cookie.setMaxAge(0);
			}
			
			// 쿠키가 사용되는 경로 지정
			cookie.setPath("/");	// localhost 밑에 모든 경로에서 사용
			
			// 생성된 쿠키를 응답 객체에 담아서 클라이어트에게 전달
			resp.addCookie(cookie);
			
			// ************************************************
			
		} else {			      // 로그인 실패
			//  기존 : HttpServletRequest; req.getHeader("referer");
			// new : @RequestHeader(value="referer") String referer
			
			path = referer; 		// 로그인 요청 전 페이지 주소(referer)
			
			// 로그인 실패 시 "아이디 또는 비밀번호가 일치하지 않습니다" 세션에 추가
			// -> 메인 페이지 주소에 message 값 노출
			// -> RedirectAttributes로 변환
			
//			model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
//			ra.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
			model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다");
		}
		return "redirect:" + path;
	}
	
	// 로그인 페이지 이동
	@GetMapping("/member/login")
	public String loginPage() {
		return "member/login";
	}
	
	// 로그아웃
	@GetMapping("/member/logout")
	public String logout(SessionStatus status, HttpSession session) {
		
		// 기존 :
		// 	HttpServletRequest req;
		//  HttpSession session = req.getSession();
		//  session.invalidate();
		//  -> 안됨
		
		// 안되는 이유 : 
		// 	 @SessionAttributes로 등록된 값은 Tomcat, Spring이 각각 관리하는 session에 등록
		//   HttpSession.invalidate(); 는 Tomcat이 관리하는 session만 삭제
		
		//   @SessionAttributes로 session scope에 등록된 값을 무효화 시키려면
		// 	 SessionStatus라는 별도의 객체를 이용해야 함

		status.setComplete();	// 세션 무효화
//		session.invalidate();
		return "redirect:/";
	}
	
	
	// 회원가입 페이지 이동
	@GetMapping("/member/signUp")
	public String signUpPage() {
		return "member/signUp";
	}
	
	// 회원가입
	@PostMapping("/member/signUp")
	public String signUp(Member inputMember /* 커맨드 객체 */, String[] memberAddress, /* name속성값이 memberAddress인 값을 배열로 반환*/
			RedirectAttributes ra,
			@RequestHeader(value="referer") String referer
			) {
		// 한글이 깨짐
		// -> POST 요청 시 인코딩 처리 필요 -> 인코딩 필터 처리(web.xml)
		
		// spring은
		// 1) 같은 name 속성을 가진 input태그의 값을
		//    값, 값, 값, 값, .... 자동으로 하나의 문자열로 만들어줌.
		
		// 2) input type="text" 의 값이 작성되지 않은 경우
		//    null이 아닌 빈 문자열("") 로 값을 얻어옴
		
		
		if(inputMember.getMemberAddress().equals(",,")) {    // 주소가 작성되지 않은 경우 == null
			inputMember.setMemberAddress(null);
			
		} else {											 // 주소가 작성된 경우 == 주소,,주소,,주소 	 
			inputMember.setMemberAddress(String.join(",,", memberAddress));
		}
		
		// 서비스 호출 후 결과 반환 받기
		int result = service.signUp(inputMember);

		String path = null; // 리다이렉트 경로 지정
		String message = null; // 전달할 메세지 저장 변수
		if(result > 0) { // 성공 시
			path = "/";
			message= "회원 가입 성공!";
			
		} else {		 // 실패 시
			path = referer;
			message = "회원 가입 실패";
			
			// 이전 페이지로 돌아갔을 때 입력했던 값을 같이 전달
			inputMember.setMemberPw(null);
			ra.addAttribute("tempMember", inputMember);
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	
	/* 스프링 예외 처리 방법 (3종류, 중복 사용 가능) 
	 * 
	 * 1 순위 : try-catch / throws 예외처리 구문 
	 * 			-> 메서드 단위로 처리
	 * 
	 * 2 순위 : @ExceptionHandler 어노테이션
	 * 			-> 클래스 단위로 처리
	 * 			- 하나의 컨트롤러에서 발생하는 예외를
	 * 			  하나의 메서드에 모아서 처리 (하나의 컨트롤러 안에 @ExceptionHandler 어노테이션이 작성된 예외처리 메서드 작성)
	 * 
	 * 3 순위 : @ControllerAdvice 어노테이션
	 * 			-> 전역(웹 어플리케이션)에서 발생하는 예외를 모아서 처리
	 * 			- 별도 클래스로 작성			 
	 * */
	
	// MemberController에서 발생하는 모든 예외를 모아서 처리
//	@ExceptionHandler(Exception.class)
//	public String exceptionHandler(Exception e, Model model) {
//		
//		// 매개변수 Exception e : 발생한 예외 전달 받는 매개변수
//		e.printStackTrace();
//		
//		model.addAttribute("errorMessage", "회원 관련 서비스 이용 중 문제가 발생했습니다.");
//		model.addAttribute("e", e);
//		
//		return "common/error";
//	}
	
}
