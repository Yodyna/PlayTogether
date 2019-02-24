package pl.opensource.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import pl.opensource.user.role.UserRole;
import pl.opensource.user.role.UserRoleRepository;

@Service
public class UserService {
	
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	private UserRepository userRepository;
	private UserRoleRepository roleRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleRepository(UserRoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	public void add(User user) {
		UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
		user.getRoles().add(defaultRole);
		userRepository.save(user);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping("/")
	public ResponseEntity<?> addUsers(@RequestBody List<User> users) {
		users.forEach(userRepository::save);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User user = userRepository.getOne(id);
		return ResponseEntity.accepted().body(user);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> putUser(@RequestBody User user) {
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		userRepository.delete(user);
		return ResponseEntity.accepted().build();
	}	
}
