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

    @GetMapping("/all-users")
    public List<UserDTO> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/friend-requests/incoming/not-reviewed")
    public List<UserDTO> getNotReviewedFriendRequests() {
        return friendshipService.getNotReviewedFriendRequests();
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId,
                               @RequestParam(value= "loadImage", defaultValue = "false") boolean loadImage){
        return userService.getUser(Integer.parseInt(userId), loadImage);
    }

    @PostMapping("/friend/request")
    public void createFriendRequest(@RequestBody Integer friendUserId) {
        friendshipService.createFriendRequest(friendUserId);
    }

}
