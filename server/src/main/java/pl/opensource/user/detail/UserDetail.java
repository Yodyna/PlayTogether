package pl.opensource.user.detail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	@Column(nullable = true)
	private String gender;
	
	@Column(nullable = true)
	private String phone;
	
	@Column(nullable = true)
	private Date birthday;
	
//	@JsonIgnore
//	@OneToOne(mappedBy="userDetail", fetch = FetchType.LAZY)
//	private User user;
}
