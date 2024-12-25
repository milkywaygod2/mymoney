package remotesoft.mymoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/") // 컨트롤러 매핑이 있으면 우선 적용없으면 → 정적 index.html로
	public String viewHome() {
		return "tViewHome";
	}
}
