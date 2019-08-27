package pl.opensource.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.advertisement.Advertisement;
import pl.opensource.user.detail.UserDetail;
import pl.opensource.user.role.UserRole;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedEntityGraph(name = "User.detail",
attributeNodes = @NamedAttributeNode("userDetail"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=5,max=32)
	@Column(length = 32, nullable = false)
	private String username;

    @JsonProperty(access = Access.WRITE_ONLY)
	@Column(length = 64, nullable = false)
	private String password;
	
    @JsonProperty(access = Access.WRITE_ONLY)
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Set<UserRole> roles = new HashSet<>();
	
    @JsonProperty(access = Access.WRITE_ONLY)
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private UserDetail userDetail;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
    private Set<Message> messages = new HashSet<>();
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	@JsonBackReference
	@ManyToMany(mappedBy = "participants", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Advertisement> returnsList;

}