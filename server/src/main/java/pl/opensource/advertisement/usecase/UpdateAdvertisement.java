package pl.opensource.advertisement.usecase;

import org.springframework.stereotype.Service;

import pl.opensource.advertisement.Advertisement;
import pl.opensource.advertisement.AdvertisementRepository;

@Service
public class UpdateAdvertisement {
	
	private final AdvertisementRepository advertisementRepository;
	
	public UpdateAdvertisement(final AdvertisementRepository advertisementRepository) {
		this.advertisementRepository = advertisementRepository;
	}
	
	public void save(Advertisement advertisement) {
		advertisementRepository.save(advertisement);
	}
}
