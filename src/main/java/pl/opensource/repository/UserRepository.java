package pl.opensource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.opensource.model.User;
import java.lang.Long;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	 User findByUsername(String username);	 
}




