package pl.opensource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.opensource.model.Advertisement;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
	
	 Advertisement findByIdAndUserUsername(Long idAdvertisement, String string);
}