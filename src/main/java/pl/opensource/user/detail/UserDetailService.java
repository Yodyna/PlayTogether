package pl.opensource.user.detail;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userDetail")
public class UserDetailService {
	
private UserDetailRepository userDetailRepository;
	
	@Autowired
	public UserDetailService(UserDetailRepository userDetailRepository) {
		this.userDetailRepository = userDetailRepository;
	}
	
	@PutMapping()
	public ResponseEntity<?> updateUserDetail(Principal principal, @RequestBody UserDetail userDetail ) {
		if(userDetail.getId() != null) {
			UserDetail userDetailOld = userDetailRepository.findById(userDetail.getId()).get();
			if(userDetailOld.getUser().getUsername().equals(principal.getName())) {
				userDetailOld = userDetail;
				userDetailRepository.save(userDetailOld);
				return ResponseEntity.status(HttpStatus.ACCEPTED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
