package andrii.dao;

import andrii.entities.UserChat;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserChatDAO extends GenericDAO<UserChat> {

    @Override
    public void save(UserChat userChat) {

    }

    @Override
    public List<UserChat> getObjects() {
        return null;
    }

    @Override
    public void update(UserChat userChat) {
        getSession().update(userChat);
    }

    @Override
    public void delete(UserChat userChat) {

    }

    public UserChat getUserChat(Integer chatId, Integer userId) {
        Query<UserChat> query = getSession().createQuery( "from UserChat us " +
                "where us.chat.id = :chatId and " +
                "us.user.id = :userId");

        query.setParameter("chatId", chatId);
        query.setParameter("userId", userId);

        return query.getSingleResult();
    }
}
