package pl.opensource.advertisement.usecase;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.opensource.advertisement.Advertisement;
import pl.opensource.advertisement.AdvertisementRepository;
import pl.opensource.sport.Sport;
import pl.opensource.user.User;
import pl.opensource.user.usecase.FindUser;

@Service
public final class CreateAdvertisement {
	
	private final AdvertisementRepository advertisementRepository;
	private final FindUser findUser;
	
	@Autowired
	public CreateAdvertisement(
			final AdvertisementRepository advertisementRepository,
			final FindUser findUser) {
		this.advertisementRepository = advertisementRepository;
		this.findUser = findUser;
	}
	
	public Advertisement create(final Advertisement advertisement, final Principal principal) {
		int min = advertisement.getMinNumberOfParticipants();
		int max = advertisement.getMaxNumberOfParticipants();
		if(min > max) {
			advertisement.setMaxNumberOfParticipants(min);
		}
		User user = findUser.findByPrincipal(principal);
		advertisement.setUser(user);
		List<User> participants = advertisement.getParticipants();
		participants.add(user);
		advertisement.setParticipants(participants);
		Sport.setAbbreviationSportInAdvertisement(advertisement);
		advertisementRepository.save(advertisement);
		return advertisement;
	}
}
