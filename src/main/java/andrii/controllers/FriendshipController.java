package andrii.controllers;

import andrii.dto.ChatDTO;
import andrii.dto.GenericDTO;
import andrii.dto.UserDTO;
import andrii.entities.UserFriendship;
import andrii.services.FriendshipService;
import andrii.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendshipController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipService friendshipService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/friend-requests/incoming/not-reviewed")
    public List<UserDTO> getNotReviewedFriendRequests() {
        return friendshipService.getFriends(UserFriendship.Status.NOT_REVIEWED);
    }

    @GetMapping("/friend-requests/incoming/rejected")
    public List<UserDTO> getRejectedFriendRequests() {
        return friendshipService.getFriends(UserFriendship.Status.REJECTED);
    }

    @GetMapping("/friend-requests/outgoing")
    public List<UserDTO> getOutgoingFriendRequests() {
        return friendshipService.getFriends(UserFriendship.Status.REQUESTED);
    }

    @GetMapping("/friends")
    public List<UserDTO> getFriends() {
        return friendshipService.getFriends(UserFriendship.Status.FRIENDS);
    }

    @PutMapping("/friends/remove")
    public void removeFriend(@RequestBody Integer friendUserId) {
        friendshipService.removeFriend(friendUserId);
    }

    @PostMapping("/friend/request")
    public void createFriendRequest(@RequestBody Integer friendUserId) {
        friendshipService.createFriendRequest(friendUserId);
        messagingTemplate
                .convertAndSendToUser(
                        userService.getUser(friendUserId).getEmail(),
                        "/queue/new-friend-request",
                        "new-friend-request");
    }

    @PutMapping("/friend/request/confirm")
    public ChatDTO confirmFriendRequest(@RequestBody Integer friendUserId) {
        return friendshipService.confirmFriendRequest(friendUserId);
    }

    @PutMapping("/friend/request/reject")
    public void rejectFriendRequest(@RequestBody Integer friendUserId) {
        friendshipService.rejectFriendRequest(friendUserId);
    }

    @PutMapping("/friend/request/outgoing/reject")
    public void rejectOutgoingFriendRequest(@RequestBody Integer friendUserId) {
        friendshipService.rejectOutgoingFriendRequest(friendUserId);
    }

    @GetMapping("/friendship/status")
    public GenericDTO<String> getFriendshipStatus(@RequestParam(value= "friendUserId") Integer friendUserId) {
        return new GenericDTO<>(
                        friendshipService.getFriendshipStatus(friendUserId));
    }

}
