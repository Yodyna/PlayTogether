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
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.advertisement.Advertisement;
import pl.opensource.user.detail.UserDetail;
import pl.opensource.user.role.UserRole;

@Data
@Entity
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=5,max=32)
	@Column(length = 32, nullable = false)
	private String username;

	@Column(length = 64, nullable = false)
	private String password;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<UserRole> roles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Advertisement> advertisements = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private UserDetail userDetail;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
}