package pl.opensource.advertisement;

import java.security.Principal;
import java.util.List;
import pl.opensource.advertisement.Sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementService {
	
	private AdvertisementRepository advertisementRepository;
	
	@Autowired
	public void setAdvertisementRepository(AdvertisementRepository advertisementRepository) {
		this.advertisementRepository = advertisementRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
		List<Advertisement> allAdvertisements = advertisementRepository.findAll();
		List<Advertisement> polishDescriptionsInSports = getPolishDescriptionsInSports(allAdvertisements);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(polishDescriptionsInSports);
	}

	private List<Advertisement> getPolishDescriptionsInSports(List<Advertisement> allAdvertisements) {
		for(Advertisement advertisement: allAdvertisements)
			for(Sport s: Sport.values()) 
				if(s.getAbbreviation().equals(advertisement.getSport())) 
					advertisement.setSport(s.getDesctiptionPL());
		return allAdvertisements;
	}
	
	@PostMapping
	public ResponseEntity<?> addAdvertisements(@RequestBody List<Advertisement> advertisements) {
		advertisements.forEach(advertisementRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> addAdvertisementByUser(Principal principal, @RequestBody Advertisement newAdvertisement) {
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(newAdvertisement.getSport())) {
				newAdvertisement.setSport(s.getAbbreviation());
				advertisementRepository.save(newAdvertisement);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAdvertisementByUser(Principal principal, @PathVariable long id) {
		Advertisement findedAdvertisement = advertisementRepository.findByIdAndUserUsername(id, principal.getName());
		return findedAdvertisement != null ? ResponseEntity.status(HttpStatus.OK).body(findedAdvertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteAdvertisementByUser(Principal principal, @RequestBody Advertisement advertisement) {
		advertisementRepository.delete(advertisement);
		return ResponseEntity.accepted().build();
	}
}
