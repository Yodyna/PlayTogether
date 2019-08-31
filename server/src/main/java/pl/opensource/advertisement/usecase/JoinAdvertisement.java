package pl.opensource.advertisement.usecase;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.opensource.advertisement.Advertisement;
import pl.opensource.advertisement.AdvertisementRepository;
import pl.opensource.user.User;
import pl.opensource.user.usecase.FindUser;

@Service
public class JoinAdvertisement {
	
	private final FindAdvertisement findAdvertisement;
	private final FindUser findUser;
	private final AdvertisementRepository advertisementRepository;
	
	public JoinAdvertisement(
			final FindAdvertisement findAdvertisement,
			final FindUser findUser,
			final AdvertisementRepository advertisementRepository) {
		this.findAdvertisement = findAdvertisement;
		this.findUser = findUser;
		this.advertisementRepository = advertisementRepository;
	}
	
	public void addUserToAdvertisement(Long id, Principal principal) {
		Advertisement advertisement = findAdvertisement.findById(id);
		User user = findUser.findByPrincipal(principal);
		List<User> participants = advertisement.getParticipants();
		boolean contains = participants.contains(user);
		if ( !contains ) {
			participants.add(user);
			advertisementRepository.save(advertisement);
		}
	}
}
