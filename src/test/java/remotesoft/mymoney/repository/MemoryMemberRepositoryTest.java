package remotesoft.mymoney.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.assertj.core.api.*; //Assertions

import remotesoft.mymoney.domain.Member;

/*public*/ class MemoryMemberRepositoryTest {
	private MemberRepository repoMember;
	
	@BeforeEach 
	public void setUp() {
		repoMember = new MemoryMemberRepository();
		Member mem1 = new Member();
		mem1.setName("Yihan1");
		Member mem2 = new Member();
		mem2.setName("Yihan2");
		repoMember.save(mem1); 
		repoMember.save(mem2); 
	}
	
	@AfterEach
	public void afterEach() {
		if (repoMember instanceof MemoryMemberRepository) {
		    ((MemoryMemberRepository) repoMember).clearStorage(); // 타입 캐스팅
		}
	}
	
	@Test
	public void save() {
		Member member = new Member();
		member.setName("testingSave");
		
		repoMember.save(member);
		
		Member result = repoMember.findById(member.getId()).get();
		
		//practiveOutputOptions
		System.out.println("result = " + (result == member));
		Assertions.assertEquals(member, result);
		// Assertions.assertThat(member).isEqualTo(result);
	}
	
	@Test
	public void findByName() {		
		Member result = repoMember.findByName("Yihan1").get();
		Assertions.assertEquals(new Member("Yihan1"), result);
	}
	
	@Test
	public void findAll() {		
		List<Member> resultList = repoMember.findAll();		
		Assertions.assertEquals(2, resultList.size(), "elements should be 2");
		// assertThat(resultList.size()).isEqualTo(2);
	}
}
