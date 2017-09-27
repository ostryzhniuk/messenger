package andrii.controllers;

import andrii.dto.UserDTO;
import andrii.services.FriendshipService;
import andrii.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PeopleController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping("/people")
    public List<UserDTO> people() {
        return userService.getUsers();
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId,
                               @RequestParam(value= "loadImage", defaultValue = "false") boolean loadImage){
        return userService.getUser(Integer.parseInt(userId), loadImage);
    }

    @PostMapping("/friendship/query")
    public void createFriendshipQuery(@RequestBody Integer friendUserId) {
        friendshipService.createFriendshipQuery(friendUserId);
    }

}
