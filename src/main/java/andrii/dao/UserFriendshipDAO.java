package andrii.dao;

import andrii.entities.UserFriendship;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<UserFriendship> get(Integer userId1, Integer userId2) {
        Query<UserFriendship> query = getSession().createQuery("select uf " +
                "from Friendship f, UserFriendship uf " +
                "where uf.friendship.id = f.id and " +
                "f.id = " +
                "   (select f2.id " +
                "   from User u1, UserFriendship uf1, Friendship f1, " +
                "        User u2, UserFriendship uf2, Friendship f2 " +
                "   where u1.id = :userId1 and " +
                "   u1.id = uf1.user.id and " +
                "   uf1.friendship.id = f1.id and " +
                "   f1.id = f2.id and " +
                "   f2.id = uf2.friendship.id and " +
                "   uf2.user.id = u2.id and " +
                "   u2.id = :userId2)");

        query.setParameter("userId1", userId1);
        query.setParameter("userId2", userId2);
        return query.getResultList();
    }

    public UserFriendship getFriendshipOfUser(Integer userToRetrieveId, Integer friendUserId) {
        Query<UserFriendship> query = getSession().createQuery("select uf1 " +
                "from User u1, UserFriendship uf1, Friendship f1, " +
                "     User u2, UserFriendship uf2, Friendship f2 " +
                "where u1.id = :userToRetrieveId and " +
                "u1.id = uf1.user.id and " +
                "uf1.friendship.id = f1.id and " +
                "f1.id = f2.id and " +
                "f2.id = uf2.friendship.id and " +
                "uf2.user.id = u2.id and " +
                "u2.id = :friendUserId");

        query.setParameter("userToRetrieveId", userToRetrieveId);
        query.setParameter("friendUserId", friendUserId);
        return query.getSingleResult();
    }

    @Override
    public void update(UserFriendship userFriendship) {
        getSession().update(userFriendship);
    }

    @Override
    public void delete(UserFriendship userFriendship) {
        Query<UserFriendship> query = getSession().createQuery("delete " +
                "from UserFriendship " +
                "where id = :userFriendshipId");

        query.setParameter("userFriendshipId", userFriendship.getId());
        query.executeUpdate();
    }

}
