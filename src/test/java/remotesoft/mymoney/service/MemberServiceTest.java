package remotesoft.mymoney.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import remotesoft.mymoney.domain.Member;
import remotesoft.mymoney.repository.MemoryMemberRepository;

// 단위테스트 (스프링컨테이너없이 순수하게 클래스테스트)
class MemberServiceTest {

	MemoryMemberRepository repoMember;
	MemberService servMember;
	
	@BeforeEach
	public void beforeEach() {
		repoMember = new MemoryMemberRepository();
		servMember = new MemberService(repoMember);
	}

	@AfterEach
	public void afterEach() {
		if (repoMember instanceof MemoryMemberRepository) {
		    ((MemoryMemberRepository) repoMember).clearStorage(); // 타입 캐스팅
		}
	}
	
	@Test
	void join() { // 회원가입
		// given
		Member mem = new Member();
		mem.setName("Hyungdu");
		
		// when
		Long saveId = servMember.join(mem);
		
		// then
		Member findMem = servMember.findOne(saveId).get();
		Assertions.assertThat(mem.getName()).isEqualTo(findMem.getName());
	}
	
	@Test
	void joinException() { // 회원가입
		// given
		Member mem1test = new Member();
		mem1test.setName("a");
		Member mem2test = new Member();
		mem1test.setName("a");
		
		// when
		servMember.join(mem1test);
		/*IllegalStateException e 
		= Assertions.assertThrows(
				IllegalStateException.class, () -> serviceMem.join(mem2test));*/
		
		try {
			servMember.join(mem2test);
			fail();
		} catch(IllegalStateException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		}
		
		// then
		
		
	}
	
	@Test
	void findMembers() {

	}

	@Test
	void findOne() {

	}

}
