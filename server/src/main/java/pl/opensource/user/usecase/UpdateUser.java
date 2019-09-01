package pl.opensource.user.usecase;

import org.springframework.stereotype.Service;

import pl.opensource.user.User;
import pl.opensource.user.UserRepository;

@Service
public final class UpdateUser {

	private final UserRepository userRepository;
	
	public UpdateUser(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
}
