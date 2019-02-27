package pl.opensource.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement,Long> {
	
	Advertisement findByIdAndUsername(Long id, String username);
}