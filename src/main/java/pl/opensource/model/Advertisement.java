package pl.opensource.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Advertisement {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 32)
	private String sport;
	
	@Column(length = 32)
	private String city;
	
	@Column(length = 512)
	private String description;
	
	@Column(length = 32)
	private String street;
	
	@Column(length = 64)
	private String url;
	
	private java.sql.Timestamp date;
	
	private java.sql.Timestamp dateOfcreate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@PrePersist
	protected void updateAuditInformation() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		dateOfcreate = new java.sql.Timestamp(now.getTime());
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getSport() {
		return sport;
	}

	public String getCity() {
		return city;
	}

	public String getDescription() {
		return description;
	}

	public String getStreet() {
		return street;
	}

	public String getUrl() {
		return url;
	}

	public java.sql.Timestamp getDate() {
		return date;
	}

	public java.sql.Timestamp getDateOfcreate() {
		return dateOfcreate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDate(java.sql.Timestamp date) {
		this.date = date;
	}

	public void setDateOfcreate(java.sql.Timestamp dateOfcreate) {
		this.dateOfcreate = dateOfcreate;
	}

	@Override
	public String toString() {
		return "Advertisement [id=" + id + ", sport=" + sport + ", city=" + city + ", description=" + description
				+ ", street=" + street + ", url=" + url + ", date=" + date + ", dateOfcreate=" + dateOfcreate
				+ ", user=" + user + "]";
	}
}
