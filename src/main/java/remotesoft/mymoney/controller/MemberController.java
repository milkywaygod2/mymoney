package remotesoft.mymoney.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import remotesoft.mymoney.domain.Member;
import remotesoft.mymoney.service.MemberService;

@Controller // 스프링컨테이너에 bean객체보관 후 DI등에 활용
public class MemberController {
	private final MemberService m_servMember;

	@Autowired
	public MemberController(MemberService servMember) {
		this.m_servMember = servMember;
	}

	@GetMapping("/member/new")
	public String createMember() {
		return "member/tViewMemberNew";
	}

	//"탬플릿이랑 같이 /members/new"로 바꿔도 작동, Get/Post경합시, 해당하는 것으로 매핑됨
	@PostMapping("/member/tViewMemberNew") 
	public String create(MemberNew newForm) {
		Member mem = new Member();
		mem.setName(newForm.getName());
		
		/*TEST*/System.out.println("memberName = " + mem.getName());
		
		m_servMember.join(mem);
		return "redirect:/";
	}
	
	@GetMapping("/member")
	public String list(Model beanModel) {
		List<Member> memsList = m_servMember.findAll();
		beanModel.addAttribute("members", memsList);
		return "member/tViewMemberList";
	}
}
