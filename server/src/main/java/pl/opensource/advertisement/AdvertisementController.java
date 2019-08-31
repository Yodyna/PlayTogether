package pl.opensource.advertisement;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pl.opensource.advertisement.usecase.CreateAdvertisement;
import pl.opensource.advertisement.usecase.DeleteAdvertisement;
import pl.opensource.advertisement.usecase.FindAdvertisement;
import pl.opensource.advertisement.usecase.JoinAdvertisement;
import pl.opensource.advertisement.usecase.UpdateAdvertisement;
import pl.opensource.message.Message;
import pl.opensource.sport.Sport;
import pl.opensource.user.User;
import pl.opensource.user.usecase.FindUser;
import pl.opensource.user.usecase.UpdateUser;

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
public class AdvertisementController {
	
	private final FindAdvertisement findAdvertisement;
	private final CreateAdvertisement createAdvertisement;
	private final JoinAdvertisement joinAdvertisement;
	private final DeleteAdvertisement deleteAdvertisement;
	private final FindUser findUser;
	private final UpdateAdvertisement updateAdvertisement;
	private final UpdateUser updateUser;
	
	@Autowired
	public AdvertisementController(
			final FindAdvertisement findAdvertisement,
			final CreateAdvertisement createAdvertisement,
			final JoinAdvertisement joinAdvertisement,
			final DeleteAdvertisement deleteAdvertisement,
			final FindUser findUser,
			final UpdateAdvertisement updateAdvertisement,
			final UpdateUser updateUser) {
		super();
		this.findAdvertisement = findAdvertisement;
		this.createAdvertisement = createAdvertisement;
		this.joinAdvertisement = joinAdvertisement;
		this.deleteAdvertisement = deleteAdvertisement;
		this.findUser = findUser;
		this.updateAdvertisement = updateAdvertisement;
		this.updateUser = updateUser;
	}
	
	@GetMapping
	public List<Advertisement> getAdvertisements() {
		return findAdvertisement.findAllAdvertisement();
	}
	
	@PostMapping("/create")
	public Advertisement createAdvertisement(Principal principal, @RequestBody Advertisement advertisement) {
		return this.createAdvertisement.create(advertisement, principal);
	}
	
	@GetMapping("/user")
	public List<Advertisement> getAdvertisementCreateByUser(Principal principal) {
		return findAdvertisement.findAllAdvertisementCreatedByPrincipal(principal);
	}
	
	@GetMapping("/userList")
	public List<Advertisement> getAllAdvertisementByUser(Principal principal) {
		return findAdvertisement.findAllAdvertisementByPrincipal(principal);
	}
	
	@GetMapping("/isParticipant/{id}")
	public ResponseEntity<?> isParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = findAdvertisement.findById(id);
		long count = advertisement.getParticipants().stream().filter( u -> u.getUsername().equals(principal.getName())).count();
		return count != 0 ? ResponseEntity.status(HttpStatus.ACCEPTED).body(true)
				: ResponseEntity.status(HttpStatus.ACCEPTED).body(false);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getAdvertisementByID(@PathVariable long id) {
		Advertisement advertisement = findAdvertisement.findById(id);
		for(Sport s: Sport.values()) {
			if(s.getAbbreviation().equals(advertisement.getSport())) {
				advertisement.setSport(s.getDesctiptionPL());
				advertisement.setActualNumberOfParticipants(advertisement.getParticipants().size());
			}
		}
		return advertisement != null ? ResponseEntity.status(HttpStatus.OK).body(advertisement)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	@GetMapping("/{sport}/{city}")
	public List<Advertisement> getAdvertisementSetBySportAndCity(@PathVariable String sport, @PathVariable String city) {
		return findAdvertisement.findAllBySportAndCity(sport, city);
	}
	
	@PostMapping("/joinToAdvertisement/{id}")
	public ResponseEntity<?> addUserToParticipant(Principal principal, @PathVariable long id) {
		joinAdvertisement.addUserToAdvertisement(id, principal);
		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/removeAdvertisement/{id}")
	public ResponseEntity<?> removeAdvertisement(Principal principal, @PathVariable long id) {
		deleteAdvertisement.removeAdvertisement(id, principal);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@DeleteMapping("/removeToAdvertisement/{id}")
	public ResponseEntity<?> removeUserToParticipant(Principal principal, @PathVariable long id) {
		Advertisement advertisement = findAdvertisement.findById(id);
		User user = findUser.findByPrincipal(principal);
		List<User> participants = advertisement.getParticipants();
		participants.remove(user);		
		updateAdvertisement.save(advertisement);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	@GetMapping("/sport")
	public ResponseEntity<?> getSportList() {
		List<String> nameSportList = Sport.getNameList();
		return ResponseEntity.accepted().body(nameSportList);
	}
	
	@PostMapping("/{id}/message")
	public ResponseEntity<Message> createMessage(Principal principal, @PathVariable long id, @RequestBody String message) {
		Advertisement advertisement = findAdvertisement.findById(id);
		List<User> participants = advertisement.getParticipants();
		List<User> participantsWithoutSender = participants.stream().filter( user -> !user.getUsername().equals(principal.getName())).collect(Collectors.toList());
		User sender = findUser.findByPrincipal(principal);

		participantsWithoutSender.forEach( user -> {
			Set<Message> messages = user.getMessages();
			Message newMessage = new Message();
			newMessage.setMessage(message);
			newMessage.setSender(sender.getUsername());
			messages.add(newMessage);
			
			user.setMessages(messages);
			updateUser.save(user);
		});
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/{id}/participants")
	public ResponseEntity<?> getParticipants(Principal principal, @PathVariable long id) {
		Advertisement advertisement = findAdvertisement.findById(id);
		List<User> participants = advertisement.getParticipants();
		List<User> participantsWithoutOwner = participants.stream().filter( user -> !user.getUsername().equals(principal.getName())).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(participantsWithoutOwner);
	}
	
	@DeleteMapping("/{advertisementId}/user/{userId}")
	public ResponseEntity<?> removeUserfromParticipant(Principal principal, @PathVariable long advertisementId, @PathVariable long userId) {
		Advertisement advertisement = findAdvertisement.findById(advertisementId);
		User participant = advertisement.getParticipants().stream().filter( user -> user.getId().equals(userId)).findFirst().get();
		advertisement.getParticipants().remove(participant);
		updateAdvertisement.save(advertisement);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PostMapping("/{advertisementId}/user/{userId}")
	public ResponseEntity<?> sendMessageToUser(Principal principal, @PathVariable long advertisementId, @PathVariable long userId, @RequestBody String message) {
		Advertisement advertisement = findAdvertisement.findById(advertisementId);
		User participant = advertisement.getParticipants().stream().filter( user -> user.getId().equals(userId)).findFirst().get();
		User sender = findUser.findByPrincipal(principal);
		Set<Message> messages = participant.getMessages();
		Message newMessage = new Message();
		newMessage.setMessage(message);
		newMessage.setSender(sender.getUsername());
		messages.add(newMessage);
		
		participant.setMessages(messages);
		updateUser.save(participant);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}