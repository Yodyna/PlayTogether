package pl.opensource.advertisement;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.user.User;

@Entity
@Data
@NoArgsConstructor
public class Advertisement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	private LocalDateTime dateOfcreate = LocalDateTime.now();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "advertisement_id", referencedColumnName="id_advertisement")
	private Set<TimeOfGame> timeOfGame = new HashSet<>();
    	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private int minNumberOfParticipants;
	
	private int actualNumberOfParticipants;
	
	private int maxNumberOfParticipants;
	
//	@ManyToMany
//	@JoinTable(name ="creditCard_choice",
//			joinColumns = @JoinColumn(name = "creditCard_id"),
//			inverseJoinColumns = @JoinColumn(name = "id_advertisement"))
//	private Set<BenefitCard> benefitCards = new HashSet<>();
}