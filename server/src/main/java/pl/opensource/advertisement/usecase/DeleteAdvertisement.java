package pl.opensource.advertisement.usecase;

import java.security.Principal;

import org.springframework.stereotype.Service;

import pl.opensource.advertisement.AdvertisementRepository;
import pl.opensource.advertisement.exception.UserIsNotOwnerAdvertisement;
import pl.opensource.user.usecase.FindUser;

@Service
public final class DeleteAdvertisement {
	
	private final AdvertisementRepository advertisementRepository;
	private final FindUser findUser;
	private final FindAdvertisement findAdvertisement;
	
	public DeleteAdvertisement(
			final AdvertisementRepository advertisementRepository,
			final FindUser findUser,
			final FindAdvertisement findAdvertisement) {
		this.advertisementRepository = advertisementRepository;
		this.findUser = findUser;
		this.findAdvertisement = findAdvertisement;
	}
	
	public void removeAdvertisement(Long id, Principal principal) {
		 if (findUser.findByPrincipal(principal) == findAdvertisement.findById(id).getUser()) {
			 advertisementRepository.deleteById(id);
		 } else {
			 throw new UserIsNotOwnerAdvertisement("Użytkownik nie jest właścicielem wydarzenia");
		 }
	}

}
