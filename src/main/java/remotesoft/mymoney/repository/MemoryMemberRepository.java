package remotesoft.mymoney.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import remotesoft.mymoney.domain.Member;

public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> storage = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member save(Member member) {
		member.setId(++sequence);
		storage.put(member.getId(), member);
		return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(storage.get(id));
	}

	@Override
	public Optional<Member> findByName(String name) {
		return storage.values().stream() // std::for_each()
				.filter(member -> member.getName().equals(name))
				.findAny();
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<>(storage.values()); // java에는 가비지컬렉터가 있다... cpp는 shared_ptr로 생성해야..
	}

}
