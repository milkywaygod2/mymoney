package remotesoft.mymoney.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import remotesoft.mymoney.domain.Member;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    // 생성자가 1개인경우 @Autowired 생략가능
    public JdbcTemplateMemberRepository(DataSource _dataSource) {
        jdbcTemplate = new JdbcTemplate(_dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate);
        insert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> params = new HashMap<>();
        params.put("name", member.getName());

        Number key = (Number) insert.executeAndReturnKey(new MapSqlParameterSource(params));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> ret = jdbcTemplate.query("SELECT * FROM member WHERE id = ?", rowMapper(), id);
        return ret.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> ret = jdbcTemplate.query("SELECT * FROM member WHERE name = ?", rowMapper(), name);
        return ret.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("SELECT * FROM member", rowMapper());
    }

    private RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> { // RowMapper<Member>
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
