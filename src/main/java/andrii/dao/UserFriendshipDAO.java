package andrii.dao;

import andrii.entities.UserFriendship;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserFriendshipDAO extends GenericDAO<UserFriendship> {

    @Override
    public void save(UserFriendship userFriendship) {
        getSession().save(userFriendship);
    }

    @Override
    public UserFriendship get(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(UserFriendship userFriendship) {
        getSession().update(userFriendship);
    }

    @Override
    public void delete(UserFriendship userFriendship) {
        getSession().delete(userFriendship);
    }
}
