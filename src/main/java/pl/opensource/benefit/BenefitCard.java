package pl.opensource.benefit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class BenefitCard {

	@Id
	@GeneratedValue
	private Long id;
	
	private String nameOfBenefitCard;
}
