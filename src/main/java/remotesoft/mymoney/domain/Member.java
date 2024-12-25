package remotesoft.mymoney.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity // jpa
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa primarykey
    @Column(name = "ID") // Entity설정시 컬럼이름과 맴버이름이 같거나 별도설정필요
	private Long m_id;

    @Column(name = "NAME") //jpa
	private String m_name;

    // Default constructor
    private static long sequence = 0L; // Static variable for unique ID generation
    public Member() {
        this.m_id = ++sequence; // Increment and assign unique ID
        this.m_name = "";
    }
    // Parameterized constructor
    public Member(String name) {
        this.m_id = ++sequence; // Increment and assign unique ID
        this.m_name = name;
    }

	public Long getId() { return m_id; }
	public Long setId(Long id) { return this.m_id = id; }
	public String getName() { return m_name; }
	public void setName(String name) { this.m_name = name; }
}

    