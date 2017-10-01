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
        getSession().save(userChat);
    }

    @Override
    public UserChat get(Integer userChatId) {
        throw new UnsupportedOperationException();
    }

    public UserChat get(Integer chatId, Integer userId) {
        Query<UserChat> query = getSession().createQuery( "from UserChat us " +
                "where us.chat.id = :chatId and " +
                "us.user.id = :userId");

        query.setParameter("chatId", chatId);
        query.setParameter("userId", userId);

        return query.getSingleResult();
    }

    @Override
    public void update(UserChat userChat) {
        getSession().update(userChat);
    }

    @Override
    public void delete(UserChat userChat) {
        getSession().delete(userChat);
    }

}
