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
public class FindAdvertisement {
	
	private final AdvertisementRepository advertisementRepository;
	private final FindUser findUser;
	
	@Autowired
	public FindAdvertisement(
			final AdvertisementRepository advertisementRepository,
			final FindUser findUser) {
		this.advertisementRepository = advertisementRepository;
		this.findUser = findUser;
	}

	public List<Advertisement> findAllAdvertisement() {
		List<Advertisement> findAllByOrderByIdDesc = advertisementRepository.findAllByOrderByIdDesc();
		Sport.setPolishDescriptionSportsInAdvertisementList(findAllByOrderByIdDesc);
		findAllByOrderByIdDesc.forEach(advertisement -> advertisement.setActualNumberOfParticipants(advertisement.getParticipants().size()));
		return findAllByOrderByIdDesc;
	}
	
	public List<Advertisement> findAllAdvertisementCreatedByPrincipal(Principal principal) {
		List<Advertisement> advertisementList = advertisementRepository.findByUserUsername(principal.getName());
		Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
		return advertisementList;
	}
	
	public List<Advertisement> findAllAdvertisementByPrincipal(Principal principal) {
		User user = findUser.findByPrincipal(principal);
		List<Advertisement> advertisementList = user.getReturnsList();
		Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
		return advertisementList;
	}
	
	public List<Advertisement> findAllBySportAndCity(String sport, String city) {
		List<Advertisement> advertisementList = null;
		for(Sport s: Sport.values()) {
			if(s.getDesctiptionPL().equals(sport)) {
				advertisementList = advertisementRepository.findAllBySportAndCity(s.getAbbreviation(), city);
				Sport.setPolishDescriptionSportsInAdvertisementList(advertisementList);
			}
		}
		return advertisementList;
	}
	
	public Advertisement findById(Long id) {
		return advertisementRepository.findById(id).get();
	}
}
