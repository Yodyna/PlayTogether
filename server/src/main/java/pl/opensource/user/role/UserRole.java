package pl.opensource.user.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class UserRole {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 32)
	private String role;
	
	@Column(length = 128)
	private String description;
}