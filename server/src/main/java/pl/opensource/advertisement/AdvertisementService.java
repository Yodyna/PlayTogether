package pl.opensource.advertisement;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.opensource.sport.Sport;
import pl.opensource.user.Message;
import pl.opensource.user.MessageRepository;
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
	private MessageRepository messageRepository;
	
	@Autowired
	public AdvertisementService(AdvertisementRepository advertisementRepository, UserRepository userRepository, MessageRepository messageRepository) {
		super();
		this.advertisementRepository = advertisementRepository;
		this.userRepository = userRepository;
		this.messageRepository = messageRepository;
	}
	
	@GetMapping
	public ResponseEntity<List<Advertisement>> getAdvertisements() {
		List<Advertisement> advertisementList = advertisementRepository.findAllByOrderByIdDesc();
		Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
		advertisementList.forEach(advertisement -> advertisement.setActualNumberOfParticipants(advertisement.getParticipants().size()));
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(advertisementList);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createAdvertisements(Principal principal, @RequestBody List<Advertisement> advertisementList) {
		User user = userRepository.findByUsername(principal.getName());
		Sport.setAbbreviationSportsInAdvertisementList(advertisementList);
		advertisementList.forEach(advertisement -> advertisement.setUser(user));
		advertisementList.forEach(advertisementRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createAdvertisement(Principal principal, @RequestBody Advertisement advertisement) {
		int min = advertisement.getMinNumberOfParticipants();
		int max = advertisement.getMaxNumberOfParticipants();
		if(min > max) {
			advertisement.setMaxNumberOfParticipants(min);
		}
		User user = userRepository.findByUsername(principal.getName());
		advertisement.setUser(user);
		List<User> participants = advertisement.getParticipants();
		participants.add(user);
		advertisement.setParticipants(participants);
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(advertisement.getSport())) {
				advertisement.setSport(s.getAbbreviation());
				advertisementRepository.save(advertisement);
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
	}
	
	@GetMapping("/user")
	public ResponseEntity<Object> getAdvertisementByUser(Principal principal) {
		List<Advertisement> advertisementList = advertisementRepository.findByUserUsername(principal.getName());
		Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(advertisementList);
	}
	
	@GetMapping("/userList")
	public ResponseEntity<?> getAllAdvertisementByUser(Principal principal) {
		User user = userRepository.findByUsername(principal.getName());
		List<Advertisement> returnsList = user.getReturnsList();
		Sport.setPolishDescriptionSportsInAdvertisementList(returnsList);
		return ResponseEntity.status(HttpStatus.OK).body(returnsList);
	}
	
	@GetMapping("/isParticipant/{id}")
	public ResponseEntity<?> isParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		long count = advertisement.getParticipants().stream().filter( u -> u.getUsername().equals(principal.getName())).count();
		return count != 0 ? ResponseEntity.status(HttpStatus.ACCEPTED).body(true)
				: ResponseEntity.status(HttpStatus.ACCEPTED).body(false);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAdvertisementByID(@PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		for(Sport s: Sport.values()) {
			if(s.getAbbreviation().equals(advertisement.getSport())) {
				advertisement.setSport(s.getDesctiptionPL());
				advertisement.setActualNumberOfParticipants(advertisement.getParticipants().size());
			}
		}
		return advertisement != null ? ResponseEntity.status(HttpStatus.OK).body(advertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAdvertisement(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findByIdAndUserUsername(id, principal.getName());
		return advertisement != null ? ResponseEntity.status(HttpStatus.OK).body(advertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/{sport}/{city}")
	public ResponseEntity<?> getAdvertisementSetBySportAndCity(@PathVariable String sport, @PathVariable String city) {
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(sport)) {
				List<Advertisement> advertisementList = advertisementRepository.findAllBySportAndCity(s.getAbbreviation(), city);
				Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
				return ResponseEntity.status(HttpStatus.CREATED).body(advertisementList);
			}
		}	
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	@PostMapping("/joinToAdvertisement/{id}")
	public ResponseEntity<?> addUserToParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		User user = userRepository.findByUsername(principal.getName());
		List<User> participants = advertisement.getParticipants();
		boolean contains = participants.contains(user);
		if ( !contains ) {
			participants.add(user);
			advertisementRepository.save(advertisement);
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/removeAdvertisement/{id}")
	public ResponseEntity<?> removeAdvertisement(Principal principal, @PathVariable long id) {
		advertisementRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
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
	
	@PostMapping("/{id}/message")
	public ResponseEntity<Message> createMessage(Principal principal, @PathVariable long id, @RequestBody String message) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		List<User> participants = advertisement.getParticipants();
		List<User> participantsWithoutSender = participants.stream().filter( user -> !user.getUsername().equals(principal.getName())).collect(Collectors.toList());
		User sender = userRepository.findByUsername(principal.getName());
		
		participantsWithoutSender.forEach( user -> {
			Set<Message> messages = user.getMessages();
			Message newMessage = new Message();
			newMessage.setMessage(message);
			newMessage.setSender(sender.getUsername());
			messages.add(newMessage);
			
			user.setMessages(messages);
			userRepository.save(user);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/{id}/participants")
	public ResponseEntity<?> getParticipants(Principal principal, @PathVariable long id) {
		Advertisement advertisement = advertisementRepository.findById(id).get();
		List<User> participants = advertisement.getParticipants();
		List<User> participantsWithoutOwner = participants.stream().filter( user -> !user.getUsername().equals(principal.getName())).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(participantsWithoutOwner);
	}
	
	@DeleteMapping("/{advertisementId}/user/{userId}")
	public ResponseEntity<?> removeUserfromParticipant(Principal principal, @PathVariable long advertisementId, @PathVariable long userId) {
		Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
		
		User participant = advertisement.getParticipants().stream().filter( user -> user.getId().equals(userId)).findFirst().get();
		advertisement.getParticipants().remove(participant);
		advertisementRepository.save(advertisement);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/{advertisementId}/user/{userId}")
	public ResponseEntity<?> sendMessageToUser(Principal principal, @PathVariable long advertisementId, @PathVariable long userId, @RequestBody String message) {
		Advertisement advertisement = advertisementRepository.findById(advertisementId).get();
		User participant = advertisement.getParticipants().stream().filter( user -> user.getId().equals(userId)).findFirst().get();
		User sender = userRepository.findByUsername(principal.getName());
		Set<Message> messages = participant.getMessages();
		Message newMessage = new Message();
		newMessage.setMessage(message);
		newMessage.setSender(sender.getUsername());
		messages.add(newMessage);
		
		participant.setMessages(messages);
		userRepository.save(participant);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}