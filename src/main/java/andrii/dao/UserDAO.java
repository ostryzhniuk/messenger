package andrii.dao;

import andrii.entities.User;
import andrii.entities.UserFriendship;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO extends GenericDAO<User> {

    @Override
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    public User get(Integer userId) {
        Query<User> query = getSession().createQuery("from User " +
                "where id = :userId");
        query.setParameter("userId", userId);
        return query.getSingleResult();
    }

    public User get(String email){
        Query<User> query = getSession().createQuery("from User " +
                "where email = :email");
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(User user) {
        getSession().delete(user);
    }

    public List<User> search(String parameter) {
        Query<User> query = getSession().createQuery("from User " +
                "where lower(firstName) like lower(concat('%', :parameter, '%'))");

        query.setParameter("parameter", parameter);
        return query.getResultList();
    }

    public List<User> getChatParticipants(Integer chatId, Integer currentUserId){
        Query<User> query = getSession().createQuery("select u " +
                "from User as u, UserChat as uc, Chat as c " +
                "where c.id = :chatId and " +
                "c.id = uc.chat.id and " +
                "uc.user.id = u.id and " +
                "u.id != :currentUserId");

        query.setParameter("chatId", chatId);
        query.setParameter("currentUserId", currentUserId);
        return query.getResultList();
    }

    public List<User> getFriendRequests(Integer userId, UserFriendship.Status status) {
        Query<User> query = getSession().createQuery("select u " +
                "from User u, UserFriendship uf, Friendship f " +
                "where u.id != :userId and " +
                "u.id = uf.user.id and " +
                "uf.friendship.id = f.id and " +
                "f.id in " +
                "   (select f.id " +
                "   from User u, UserFriendship uf, Friendship f " +
                "   where u.id = :userId and " +
                "   u.id = uf.user.id and " +
                "   uf.status = :status and " +
                "   uf.friendship.id = f.id)");

        query.setParameter("userId", userId);
        query.setParameter("status", status);
        return query.getResultList();
    }

}
