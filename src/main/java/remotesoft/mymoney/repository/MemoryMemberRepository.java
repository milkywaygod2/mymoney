package remotesoft.mymoney.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import remotesoft.mymoney.domain.Member;


//@Repository
public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> m_storage = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member save(Member member) {
		m_storage.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(m_storage.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		return m_storage.values().stream() // std::for_each()
				.filter(member -> member.getName().equals(name))
				.findAny();
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(m_storage.values()); // java에는 가비지컬렉터가 있다... cpp는 shared_ptr로 생성해야..
	}
	
	public void clearStorage() {
		m_storage.clear();
	}

}
