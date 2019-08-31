package pl.opensource.advertisement;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.timeofgame.TimeOfGame;
import pl.opensource.user.User;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedEntityGraph(name = "Advertisement.detail",
attributeNodes = @NamedAttributeNode("participants"))
public class Advertisement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(length = 4)
	private String sport;
	
	@NotNull
	@Column(length = 32)
	private String city;
	
	@Column(length = 256)
	private String description;
	
	@NotNull
	@Column(length = 32)
	private String street;
	
	@Column(length = 64)
	private String url;
	
	private LocalDateTime dateOfcreate = LocalDateTime.now();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "advertisement_id", referencedColumnName="id")
	private Set<TimeOfGame> timeOfGame = new HashSet<>();
    	
	@ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Min(2)
	private int minNumberOfParticipants;
		
	@Min(2)
	private int maxNumberOfParticipants;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "participants",
    		joinColumns = {@JoinColumn(name="advertisement_id", referencedColumnName="id")},
    		inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
    )
	private List<User> participants = new ArrayList<>();	
	
	@Transient
	private int actualNumberOfParticipants;
}
	