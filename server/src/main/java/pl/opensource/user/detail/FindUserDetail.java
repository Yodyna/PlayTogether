package pl.opensource.user.detail;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.opensource.user.User;
import pl.opensource.user.UserRepository;

@Service	
public class FindUserDetail {
	
	private final UserRepository userRepository;
	
	@Autowired
	public FindUserDetail(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDetail findByPrincipal(Principal principal) {
		
		User user = userRepository.findByUsername(principal.getName());
		UserDetail userDetail = user.getUserDetail();
		Gender.setPolishDescription(userDetail);
		return userDetail;
	}
}
