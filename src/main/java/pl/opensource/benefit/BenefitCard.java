package pl.opensource.benefit;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class BenefitCard {

	private boolean MultiSport;
	private boolean BenefitSystems;
	private int discount;
	
}
