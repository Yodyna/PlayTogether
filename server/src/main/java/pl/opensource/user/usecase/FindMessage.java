package pl.opensource.user.usecase;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.opensource.message.Message;
import pl.opensource.user.UserRepository;
import pl.opensource.user.exception.UserIsNotLoggedInException;

@Service
public final class FindMessage {
	
	private final UserRepository userRepository;
	
	@Autowired
	public FindMessage(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public Set<Message> findByPrincipal(Principal principal) {
		if(principal == null) {
			throw new UserIsNotLoggedInException("Użytkownik nie jest zalogowany");
		}
		return userRepository.findByUsername(principal.getName()).getMessages();
	}
}
