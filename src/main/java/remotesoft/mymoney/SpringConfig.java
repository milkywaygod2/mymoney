package remotesoft.mymoney;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import remotesoft.mymoney.aop.TimeTraceAop;
import remotesoft.mymoney.repository.*;
//import remotesoft.mymoney.repository.JdbcTemplateMemberRepository;
import remotesoft.mymoney.service.MemberService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

	//SpringDataJPA
	private final MemberRepository m_memberRepository;

	@Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.m_memberRepository = memberRepository;
    }

    //jpa
//	//@PersistenceContext //stack에서는
//	private EntityManager m_entityManager;
//
//	@Autowired
//	public SpringConfig(EntityManager entityManager) {
//		this.m_entityManager = entityManager;
//	}

	//before jpa include jdbc
//	private final DataSource m_dataSource;
//
//	@Autowired
//	public SpringConfig(DataSource dataSource) {
//		this.m_dataSource = dataSource;
//	}

	@Bean
	public MemberService beanMemberService() {
//		return new MemberService(beanMemberRepository());
		return new MemberService(m_memberRepository);
	}
	
//	@Bean
//	public MemberRepository beanMemberRepository() { // 다형성 생성자
////		return new MemoryMemberRepository();
////		return new JdbcMemberRepository(m_dataSource);
////		return new JdbcTemplateMemberRepository(m_dataSource);
//		return new JpaMemberRepository(m_entityManager);
//	}

	@Bean
	public TimeTraceAop aop_timetrace() {
		return new TimeTraceAop();
	}
}
