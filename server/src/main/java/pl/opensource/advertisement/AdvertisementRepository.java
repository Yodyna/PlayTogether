package pl.opensource.advertisement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
	
	Advertisement findByIdAndUserUsername(Long id, String username);
	List<Advertisement> findByUserUsername(String username);
	List<Advertisement> findAllBySportAndCity(String sport, String city);
	 
}