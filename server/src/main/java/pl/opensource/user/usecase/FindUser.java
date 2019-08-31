package pl.opensource.user.usecase;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.opensource.user.User;
import pl.opensource.user.UserRepository;

@Service
public final class FindUser {

	private final UserRepository userRepository;
	
	@Autowired
	public FindUser(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findByPrincipal(Principal principal) {	
		return userRepository.findByUsername(principal.getName());
	}
}
