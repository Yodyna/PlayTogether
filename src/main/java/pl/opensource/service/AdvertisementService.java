package pl.opensource.service;

import java.security.Principal;
import java.util.List;

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

import pl.opensource.model.Advertisement;
import pl.opensource.repository.AdvertisementRepository;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementService {
	
	private AdvertisementRepository advertisementRepository;
	
	@Autowired
	public void setAdvertisementRepository(AdvertisementRepository advertisementRepository) {
		this.advertisementRepository = advertisementRepository;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
		List<Advertisement> FindAlladvertisements = advertisementRepository.findAll();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(FindAlladvertisements);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addAdvertisements(@RequestBody List<Advertisement> advertisements) {
		advertisements.forEach(advertisementRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/user/")
	public ResponseEntity<?> addAdvertisementByUser(Principal principal, @RequestBody Advertisement newAdvertisement) {
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAdvertisementByUser(Principal principal, @PathVariable long id) {
		Advertisement findByIdAndUserUsername = advertisementRepository.findByIdAndUserUsername(id, principal.getName());
		if(findByIdAndUserUsername != null)
			return ResponseEntity.status(HttpStatus.OK).body(findByIdAndUserUsername);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteAdvertisementByUser(Principal principal, @RequestBody Advertisement advertisement) {
		advertisementRepository.delete(advertisement);
		return ResponseEntity.accepted().build();
	}

}
