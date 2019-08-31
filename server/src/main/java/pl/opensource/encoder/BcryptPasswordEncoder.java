package pl.opensource.encoder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pl.opensource.user.User;
import pl.opensource.user.role.UserRole;
import pl.opensource.user.role.UserRoleRepository;

@Service
public final class BcryptPasswordEncoder {

	private static final String DEFAULT_ROLE = "ROLE_USER";
	private final UserRoleRepository userRoleRepository;

	@Autowired
	public BcryptPasswordEncoder(final UserRoleRepository userRoleRepository) {
		this.userRoleRepository = userRoleRepository;
	}
	
	public User encode(User user) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		final UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder()
				.passwordEncoder(encoder::encode);
		UserDetails user12 = userBuilder.username(user.getUsername()).password(user.getPassword()).roles("USER")
				.build();
		user.setPassword(user12.getPassword().substring(8));
		UserRole defaultRole = userRoleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		return user;
	}
}
