package pl.opensource.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeOfGameRepository extends JpaRepository<TimeOfGame,Long> {
	
}
