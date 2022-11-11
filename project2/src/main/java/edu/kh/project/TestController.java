package edu.kh.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/test")
@Controller
public class TestController {
	
	@GetMapping("/param")
	public String param(RedirectAttributes ra) {
		ra.addFlashAttribute("testParam", "테스트용 파라미터");
		return "forward:/test/param2";
	}
	
	@GetMapping("/param2")
	public String param2(String testParam) {
		System.out.println(testParam);
			
		return "redirect:/";
	}
}
