package pl.opensource.advertisement;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;
import pl.opensource.benefit.BenefitCard;
import pl.opensource.user.User;

@Entity
@Data
public class Advertisement {
	
	@Id
	@GeneratedValue
	@Column(name = "id_advertisement")
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

	@ManyToMany
	@JoinTable(name ="creditCard_choice",
			joinColumns = @JoinColumn(name = "id_advertisement"),
			inverseJoinColumns = @JoinColumn(name = "id_creditcard"))
	private Set<BenefitCard> benefitCards = new HashSet<>();
}
