package andrii.services;

import andrii.dao.FriendshipDAO;
import andrii.dao.UserFriendshipDAO;
import andrii.entities.Friendship;
import andrii.entities.User;
import andrii.entities.UserFriendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipDAO friendshipDAO;

    @Autowired
    private UserFriendshipDAO userFriendshipDAO;

    @Autowired
    private UserService userService;

    @Transactional
    public void createFriendshipQuery(Integer friendUserId) {

        User currentUser = userService.getUser(userService.getCurrentUserId());
        User friendUser = userService.getUser(friendUserId);

        Friendship friendship = new Friendship();
        friendshipDAO.save(friendship);


        UserFriendship friendshipCurrentUser = new UserFriendship
                .UserFriendshipBuilder(friendship, currentUser)
                .buildSubscriber();
        userFriendshipDAO.save(friendshipCurrentUser);

        UserFriendship friendshipFriendUser = new UserFriendship
                .UserFriendshipBuilder(friendship, friendUser)
                .buildNotReviewed();
        userFriendshipDAO.save(friendshipFriendUser);
    }

}
