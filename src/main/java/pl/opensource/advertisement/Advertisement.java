package pl.opensource.advertisement;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Data;
import pl.opensource.user.User;

@Entity
@Data
public class Advertisement {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 4)
	private String sport;
	
	@Column(length = 32)
	private String city;
	
	@Column(length = 512)
	private String description;
	
	@Column(length = 32)
	private String street;
	
	@Column(length = 64)
	private String url;
	
	private LocalDateTime date;
	private LocalDateTime dateOfcreate = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
