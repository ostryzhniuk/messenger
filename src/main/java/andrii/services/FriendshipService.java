package andrii.services;

import andrii.dao.FriendshipDAO;
import andrii.dao.UserDAO;
import andrii.dao.UserFriendshipDAO;
import andrii.dto.UserDTO;
import andrii.entities.Friendship;
import andrii.entities.User;
import andrii.entities.UserFriendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipDAO friendshipDAO;

    @Autowired
    private UserFriendshipDAO userFriendshipDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserService userService;

    @Transactional
    public void createFriendRequest(Integer friendUserId) {

        User currentUser = userService.getUser(userService.getCurrentUserId());
        User friendUser = userService.getUser(friendUserId);

        Friendship friendship = new Friendship();
        friendshipDAO.save(friendship);


        UserFriendship friendshipCurrentUser = new UserFriendship
                .UserFriendshipBuilder(friendship, currentUser)
                .buildRequested();
        userFriendshipDAO.save(friendshipCurrentUser);

        UserFriendship friendshipFriendUser = new UserFriendship
                .UserFriendshipBuilder(friendship, friendUser)
                .buildNotReviewed();
        userFriendshipDAO.save(friendshipFriendUser);
    }

    @Transactional
    public List<UserDTO> getFriends(UserFriendship.Status status) {

        List<User> userList = userDAO.getFriendRequests(
                userService.getCurrentUserId(),
                status);

        List<UserDTO> userDTOList = userService.convertToDTOList(userList);
        userDTOList
                .forEach(userDTO ->
                        userDTO.setPhoto(userService.loadPhoto(userDTO.getId())));

        return userDTOList;
    }

}
