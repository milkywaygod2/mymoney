package remotesoft.mymoney.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import remotesoft.mymoney.domain.Member;
import remotesoft.mymoney.repository.MemberRepository;
import remotesoft.mymoney.repository.MemoryMemberRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest // 스프링컨테이너와 테스트를 함께 실행한다. (통합테스트)
@Transactional // 테스트만해보고 테스트 끝나면 커밋안하기
class MemberServiceIntegrationTest {

	@Autowired MemberService m_servMember;
	@Autowired MemberRepository m_repoMember;

	@Test
	// @Commit // @Transcation에도 불구하고 커밋하는 예외 
	void join() { // 회원가입
		// given
		Member mem = new Member();
		mem.setName("Hyungdu1");
		
		// when
		Long saveId = m_servMember.join(mem);
		
		// then
//		Member findMem = m_servMember.findOne(saveId).get();
//		assertThat(mem.getName()).isEqualTo(findMem.getName());
		Optional<Member> optionalMember = m_servMember.findOne(saveId);
		if (optionalMember.isPresent()) {
			Member findMem = optionalMember.get();
			assertThat(mem.getName()).isEqualTo(findMem.getName());
		} else {
			throw new NoSuchElementException("No member found with ID: " + saveId);
		}
	}
	
	@Test
	void joinException() { // 회원가입
		// given
		Member mem1test = new Member();
		mem1test.setName("a");
		Member mem2test = new Member();
		mem2test.setName("a");
		
		// when
		m_servMember.join(mem1test);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> m_servMember.join(mem2test));

		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
	}
}
