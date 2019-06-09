package pl.opensource.advertisement;


import java.security.Principal;
import java.util.ArrayList;
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
	public AdvertisementService(AdvertisementRepository advertisementRepository, UserRepository userRepository) {
		super();
		this.advertisementRepository = advertisementRepository;
		this.userRepository = userRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Advertisement>> getAdvertisements() {
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
	
	@PostMapping("/")
	public ResponseEntity<?> createAdvertisements(Principal principal, @RequestBody List<Advertisement> advertisements) {
		User user = userRepository.findByUsername(principal.getName());
		List<Advertisement> setAbbreviationsInSports = setAbbreviationsInSports(advertisements);
		setAbbreviationsInSports.forEach(advertisement -> advertisement.setUser(user));
		setAbbreviationsInSports.forEach(advertisementRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createAdvertisement(Principal principal, @RequestBody Advertisement newAdvertisement) {
		User user = userRepository.findByUsername(principal.getName());
		newAdvertisement.setUser(user);
		List<User> participants = newAdvertisement.getParticipants();
		participants.add(user);
		newAdvertisement.setParticipants(participants);
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(newAdvertisement.getSport())) {
				newAdvertisement.setSport(s.getAbbreviation());
				advertisementRepository.save(newAdvertisement);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
	}
	
	@GetMapping("/user")
	public ResponseEntity<Object> getAdvertisementByUser(Principal principal) {
		List<Advertisement> advertisementList = advertisementRepository.findByUserUsername(principal.getName());
		List<Advertisement> polishDescriptionsInSports = getPolishDescriptionsInSports(advertisementList);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(polishDescriptionsInSports);
	}
	
	@GetMapping("/isParticipant/{id}")
	public ResponseEntity<?> isParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		long count = advertisement.getParticipants().stream().filter( u -> u.getUsername().equals(principal.getName())).count();
		return count != 0? ResponseEntity.status(HttpStatus.ACCEPTED).body(true) : ResponseEntity.status(HttpStatus.ACCEPTED).body(false);
	}
	
	@GetMapping("/getParticipantCount/{id}")
	public ResponseEntity<?> getParticipantCount(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		int size = advertisement.getParticipants().size();
		return ResponseEntity.status(HttpStatus.OK).body(size);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAdvertisementByID(@PathVariable long id) {
		Advertisement findedAdvertisement = advertisementRepository.findById(id).get();
		for(Sport s: Sport.values()) {
			if(s.getAbbreviation().equals(findedAdvertisement.getSport())) {
				findedAdvertisement.setSport(s.getDesctiptionPL());
			}
		}
		return findedAdvertisement != null ? ResponseEntity.status(HttpStatus.OK).body(findedAdvertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAdvertisement(Principal principal, @PathVariable long id) {
		Advertisement findedAdvertisement = advertisementRepository.findByIdAndUserUsername(id, principal.getName());
		return findedAdvertisement != null ? ResponseEntity.status(HttpStatus.OK).body(findedAdvertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/{sport}/{city}")
	public ResponseEntity<?> getAdvertisementSetBySportAndCity(@PathVariable String sport, @PathVariable String city) {
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(sport)) {
				List<Advertisement> findAllBySportAndCity = advertisementRepository.findAllBySportAndCity(s.getAbbreviation(), city);
				List<Advertisement> advertisementListInPolishDescription = getPolishDescriptionsInSports(findAllBySportAndCity);
				return ResponseEntity.status(HttpStatus.CREATED).body(advertisementListInPolishDescription);
			}
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@PostMapping("/joinToAdvertisement/{id}")
	public ResponseEntity<?> addUserToParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		User user = userRepository.findByUsername(principal.getName());
		List<User> participants = advertisement.getParticipants();
		participants.add(user);
		advertisementRepository.save(advertisement);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/removeToAdvertisement/{id}")
	public ResponseEntity<?> removeUserToParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		User user = userRepository.findByUsername(principal.getName());
		List<User> participants = advertisement.getParticipants();
		participants.remove(user);
		advertisementRepository.save(advertisement);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@GetMapping("/sport")
	public ResponseEntity<?> getSportList() {
		List<String> sports = new ArrayList<String>();
		for(Sport sport: Sport.values()) {
			sports.add(sport.getDesctiptionPL());
		}
		return ResponseEntity.accepted().body(sports);
	}
}