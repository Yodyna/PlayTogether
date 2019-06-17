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
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.opensource.user.User;

@Data
@Entity
@NoArgsConstructor
public class Advertisement {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 4)
	private String sport;
	
	@Column(length = 32)
	private String city;
	
	@Column(length = 256)
	private String description;
	
	@Column(length = 32)
	private String street;
	
	@Column(length = 64)
	private String url;
	
	private LocalDateTime dateOfcreate = LocalDateTime.now();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "advertisement_id", referencedColumnName="id")
	private Set<TimeOfGame> timeOfGame = new HashSet<>();
    	
	@JsonIgnore
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	private int minNumberOfParticipants;
		
	private int maxNumberOfParticipants;
	
	@JsonIgnore
	@ManyToMany(fetch= FetchType.LAZY) //change to lazy
    @JoinTable(name = "participants",
    		joinColumns = {@JoinColumn(name="advertisement_id", referencedColumnName="id")},
    		inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")}
    )
	private List<User> participants = new ArrayList<>();	
}
	