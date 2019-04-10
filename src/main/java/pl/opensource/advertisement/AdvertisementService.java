package pl.opensource.advertisement;

import java.security.Principal;
import java.util.List;

import pl.opensource.advertisement.Sport;
import pl.opensource.user.User;
import pl.opensource.user.UserRepository;

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
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
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
		for(Advertisement advertisement: allAdvertisements) {
			for(Sport s: Sport.values()) {
				if(s.getAbbreviation().equals(advertisement.getSport())) {
					advertisement.setSport(s.getDesctiptionPL());
				}
			}
		}
		return allAdvertisements;
	}
	
	private List<Advertisement> setAbbreviationsInSports(List<Advertisement> advertisements) {
		for(Advertisement advertisement: advertisements) {
			for(Sport s: Sport.values()) {
				if(s.getDesctiptionPL().equals(advertisement.getSport())) {
					advertisement.setSport(s.getAbbreviation());
				}
			}
		}
		return advertisements;
	}
	
	@PostMapping
	public ResponseEntity<?> addAdvertisements(Principal principal, @RequestBody List<Advertisement> advertisements) {
		User user = userRepository.findByUsername(principal.getName());
		List<Advertisement> setAbbreviationsInSports = setAbbreviationsInSports(advertisements);
		setAbbreviationsInSports.forEach(advertisement -> advertisement.setUser(user));
		setAbbreviationsInSports.forEach(advertisementRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> addAdvertisementByUser(Principal principal, @RequestBody Advertisement newAdvertisement) {
		User user = userRepository.findByUsername(principal.getName());
		newAdvertisement.setUser(user);
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(newAdvertisement.getSport())) {
				newAdvertisement.setSport(s.getAbbreviation());
				Advertisement save = advertisementRepository.save(newAdvertisement);
				System.out.println("Sprawdzam poprawnosc " + save.getTimeOfGame().toString());
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