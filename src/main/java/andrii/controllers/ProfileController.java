package andrii.controllers;

import andrii.dto.UserDTO;
import andrii.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId){
        return userService.getUserById(Integer.parseInt(userId));
    }

    @GetMapping("/userId")
    public Integer getUserId(@RequestParam(value= "email") String email){
        return userService.getUserId(email);
    }

}