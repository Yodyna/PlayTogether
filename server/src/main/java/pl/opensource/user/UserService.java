package pl.opensource.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.opensource.contact.SendEmail;
import pl.opensource.user.detail.Gender;
import pl.opensource.user.detail.UserDetail;
import pl.opensource.user.detail.UserDetailRepository;
import pl.opensource.user.role.UserRole;
import pl.opensource.user.role.UserRoleRepository;

@RestController
@RequestMapping("/user")
public class UserService {

	private static final String DEFAULT_ROLE = "ROLE_USER";

	private UserRepository userRepository;
	private UserRoleRepository roleRepository;

	@Autowired
	private SendEmail sendEmail;

	@Autowired
	public UserService(UserRepository userRepository, UserRoleRepository roleRepository,
			UserDetailRepository userDetailRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public void add(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
	}

	@GetMapping("/")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
	}

	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (userRepository.findByUsername(user.getUsername()) == null) {
			encoderPassword(user);
			userRepository.save(user);
			sendEmail.welcome(user);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	private void encoderPassword(User user) {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		final UserBuilder userBuilder = org.springframework.security.core.userdetails.User.builder()
				.passwordEncoder(encoder::encode);
		UserDetails user12 = userBuilder.username(user.getUsername()).password(user.getPassword()).roles("USER")
				.build();
		user.setPassword(user12.getPassword().substring(8));
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
	}

	@PostMapping("/")
	public ResponseEntity<?> createUsers(@RequestBody List<User> users) {
		users.forEach(userRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/detail")
	public ResponseEntity<?> getUserDetail(Principal principal) {
		User user = userRepository.findByUsername(principal.getName());
		UserDetail userDetail = user.getUserDetail();
		Gender.setPolishDescription(userDetail);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDetail);
	}
}
