package pl.opensource.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.advertisement.Advertisement;
import pl.opensource.user.role.UserRole;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 32, nullable = false)
	private String username;

	@Column(length = 32, nullable = false)
	private String password;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<UserRole> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private Set<Advertisement> advertisements = new HashSet<>();

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}