package remotesoft.mymoney.service;


import org.springframework.transaction.annotation.Transactional;
import remotesoft.mymoney.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import remotesoft.mymoney.domain.Member;


//@Service //mvcDefaultAnnotation insteadof Setting @Bean in @Configuration
@Transactional // for DB, 특히 jpa
public class MemberService {
	
	//의존성주입 (사용목적이 싱글톤과 유사, 객체관리목적)
	private final MemberRepository m_repoMember;
	
	@Autowired
	public MemberService(MemberRepository repoMember) {
		this.m_repoMember = repoMember;
	}
	
	//회원가입
	public Long join(Member mem) {

		long startTime = System.currentTimeMillis();

		try	{
			validateDuplicateMember(mem);
			m_repoMember.save(mem);
			return mem.getId();
		} finally {
			long endTime = System.currentTimeMillis();
			long elapsedTime = endTime - startTime;
			System.out.println("join : " + elapsedTime + "ms");
		}

	}

	private void validateDuplicateMember(Member mem) {
		//findByName은 optional<Member> 리턴, 
		//ifPresent는 optional의 값을 검사하고, 다시 그 값을 인자로 하는 Consumer함수를 인자로 실행함
		m_repoMember.findByName(mem.getName()).ifPresent(_optionalValue -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});	
	}
	
	public List<Member> findAll() {
		return m_repoMember.findAll();
	}
	
	public Optional<Member> findOne(Long memId) {
		return m_repoMember.findById(memId);
	}
}
