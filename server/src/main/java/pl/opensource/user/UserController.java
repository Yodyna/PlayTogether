package pl.opensource.user;

import java.security.Principal;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.opensource.message.Message;
import pl.opensource.user.detail.FindUserDetail;
import pl.opensource.user.detail.UserDetail;
import pl.opensource.user.usecase.CreateUser;
import pl.opensource.user.usecase.FindMessage;

@RestController
@RequestMapping("/user")
public class UserController {

	private final CreateUser createUser;
	private final FindUserDetail findUserDetail;
	private final FindMessage findMessage;

	@Autowired
	public UserController(
			final CreateUser createUser,
			final FindMessage findMessage,
			final FindUserDetail findUserDetail) {
		
		this.createUser = createUser;
		this.findMessage = findMessage;
		this.findUserDetail = findUserDetail;
	}

	@PostMapping("/register")
	public User createUser(@RequestBody final User user) {
				
		return createUser.create(user);
	}

	@GetMapping("/detail")
	public UserDetail getUserDetail(Principal principal) {
		
		return findUserDetail.findByPrincipal(principal);
	}
	
	@GetMapping("/message")
	public Set<Message> getUserMesssage(Principal principal) {
		
		return findMessage.findByPrincipal(principal);
	}
}
