package pl.opensource.user;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "registerForm";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@RequestMapping("/welcome")
	@ResponseBody
	public Principal secured(Principal principal) {
		return principal;
	}
}