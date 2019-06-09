package pl.opensource.user.detail;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.user.User;

@Data
@Entity
@NoArgsConstructor
public class UserDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	@Column(nullable = true)
	private Gender gender;
	
	@Column(nullable = true)
	private String phone;
	
	@Column(nullable = true)
	private Date birthday;
	
	@JsonIgnore
	@OneToOne(mappedBy="userDetail")
	private User user;
}
