package pl.opensource.user.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.opensource.contact.SendEmail;
import pl.opensource.encoder.BcryptPasswordEncoder;
import pl.opensource.user.User;
import pl.opensource.user.UserRepository;
import pl.opensource.user.exception.UserAlreadyExistsException;

@Service
public final class CreateUser {
	
	private final UserRepository userRepository;
	private final BcryptPasswordEncoder bcryptPasswordEncoder;
	private final SendEmail sendEmail;
	
	@Autowired
	public CreateUser(final UserRepository userRepository, final BcryptPasswordEncoder bcryptPasswordEncoder, final SendEmail sendEmail) {
		this.userRepository = userRepository;
		this.bcryptPasswordEncoder = bcryptPasswordEncoder;
		this.sendEmail = sendEmail;
	}
	
	public User create(final User user) {
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new UserAlreadyExistsException(user.getUsername());
		}
		
		bcryptPasswordEncoder.encode(user);
		return userRepository.save(user);
	}
}
