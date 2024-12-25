package remotesoft.mymoney.repository;

import jakarta.persistence.EntityManager;
import remotesoft.mymoney.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager m_entityManager; // jpa

    public JpaMemberRepository(EntityManager em) {
        this.m_entityManager = em;
    }

    @Override
    public Member save(Member member) {
        if (member.getId() == null) {
            m_entityManager.persist(member);
        } else {
            m_entityManager.merge(member);
        }
        return member;
    }


    @Override
    public Optional<Member> findById(Long id) {
        Member member = m_entityManager.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) { // jpa문법 :_name 파라미터에 이름부여
        List<Member> ret = m_entityManager
                .createQuery("SELECT tableClass FROM Member AS tableClass WHERE tableClass.m_name = :NAME", Member.class)
                .setParameter("NAME", name)
                .getResultList();
        return ret.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return m_entityManager.createQuery("SELECT m FROM Member AS m", Member.class)
                .getResultList(); //jpa-jpql기술 테이블이 아니라 등록된entity클래스로 쿼리를 날림
    }
}
