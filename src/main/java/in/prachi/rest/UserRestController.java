package in.prachi.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

	@GetMapping(value="/admin")
	public String admin() {
		return "welcome Admin";
	}

	@GetMapping(value="/user")
	public String user() {
		return "welcome user";
	}
}

	
