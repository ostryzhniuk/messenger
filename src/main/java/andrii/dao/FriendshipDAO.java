package andrii.dao;

import andrii.entities.Friendship;
import andrii.entities.UserFriendship;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class FriendshipDAO extends GenericDAO<Friendship> {

    @Override
    public void save(Friendship friendship) {
        getSession().save(friendship);
    }

    @Override
    public Friendship get(Integer id) {
        throw new UnsupportedOperationException();
    }

    public String getFriendshipStatus(Integer currentUserId, Integer secondUserId) {

        Query<UserFriendship.Status> query = getSession().createQuery("select uf1.status " +
                "from User u1, UserFriendship uf1, Friendship f1, " +
                "     User u2, UserFriendship uf2, Friendship f2 " +
                "where u1.id = :currentUserId and " +
                "u1.id = uf1.user.id and " +
                "uf1.friendship.id = f1.id and " +
                "f1.id = f2.id and " +
                "f2.id = uf2.friendship.id and " +
                "uf2.user.id = u2.id and " +
                "u2.id = :secondUserId");

        query.setParameter("currentUserId", currentUserId);
        query.setParameter("secondUserId", secondUserId);

        String stringStatus;

        try {
            UserFriendship.Status status = query.getSingleResult();
            stringStatus = status.name();
        } catch (NoResultException e) {
            stringStatus = "NOT_FRIENDS";
        }
        return stringStatus;
    }

    @Override
    public void update(Friendship friendship) {
        getSession().update(friendship);
    }

    @Override
    public void delete(Friendship friendship) {
        getSession().delete(friendship);
    }
}
