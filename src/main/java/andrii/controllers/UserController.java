package andrii.controllers;

import andrii.dto.AuthenticationDTO;
import andrii.dto.UserDTO;
import andrii.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/currentUser")
    public AuthenticationDTO currentUser(){
        return userService.getCurrentUser();
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId,
                               @RequestParam(value= "loadImage", defaultValue = "false") boolean loadImage){
        return userService.getUser(Integer.parseInt(userId), loadImage);
    }

    @GetMapping("/search")
    public List<UserDTO> search(@RequestParam(value= "parameter") String parameter){
        return userService.search(parameter);
    }

}
