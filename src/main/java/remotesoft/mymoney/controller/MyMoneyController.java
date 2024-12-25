package remotesoft.mymoney.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyMoneyController {
	//1. static.html은 컨트롤러도 필요없음 주소로 바로접근
	
	//2. template으로 만든 view를 리턴하는 방법
	@GetMapping("tViewHello") //from index.html
	public String hello(Model beanModel) {
		beanModel.addAttribute("keyName", "몽두~~");
		return "tViewHello"; //to tViewHello.html
	}
	
	// template + requestParam 조합
	@GetMapping("tViewHelloTemplate") // /tViewHelloMVC?adressId={id}, 정적html은 확장자필수 but 템플릿엔진 사용시 생략가능
	public String helloMVC(@RequestParam("addressId") String id, Model beanModel) {
		beanModel.addAttribute("keyName", id); // html 본문에서 사용할 키-벨류
		return "tViewHelloTemplate";
	}

	//3. 메소드리턴타입 리턴 (api방식)
	@GetMapping("noView-noHTML") // https:.../noView-noHTML?adressId={id}
	@ResponseBody // html태그가 아니라 문자열을 그냥 넘김, 본디forAPI방식
	public String helloResponse(@RequestParam("addressId") String id) {
		return "there is no html-view " + id;
	}
	
	/* 원래 서버가 주소받으면 ViewResolver가 작동 → html검색, html파일이름 리턴
	 * 리스폰스바디 이노테이션시 HttpMessageConverter가 작동 → pure메소드 리턴
	 * → 문자StringHttpMessageConverter 
	 * → 객체MappingJackson2HttpMessageConverter
	 * → 바이트 처리등등 기본지원 */
	// api방식 json예제
	@GetMapping("noView-HelloApi") // https:.../noView-HelloApi?adressId={id}
	@ResponseBody
	public HelloApi runHelloApi(@RequestParam("addressId") String id) {
		HelloApi helloData = new HelloApi();
		helloData.setId(id); // 리퀘스트
		return helloData; // 맴버변수만 선언순서대로 JSON으로 쭉 나감, 요새 표준
	}
	
	static class HelloApi {
		private String m_id;
		private int m_n1 = 1;
		private float m_f1 = 1.f;
		
		public int getN1() { return m_n1; }
		public void setN1(int n1) { this.m_n1 = n1; }
		
		public float getF1() { return m_f1; }
		public void setF1(float f1) { this.m_f1 = f1; }
		
		public String getId() { return m_id; }
		public void setId(String id) { this.m_id = id; }
	}
}
