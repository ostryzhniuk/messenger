package andrii.dao;

import andrii.entities.Chat;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Transactional
public class ChatDAO extends GenericDAO<Chat> {

    @Override
    public void save(Chat chat) {

    }

    @Override
    public List<Chat> getObjects() {
        return null;
    }

    @Override
    public void update(Chat chat) {

    }

    @Override
    public void delete(Chat chat) {

    }

    public Chat getChat(Integer userId1, Integer userId2) {
        Query<Chat> query = getSession().createQuery("select c " +
                "from Chat c " +
                "where c.id = " +
                    "(select uc1.chat.id " +
                    "from UserChat uc1 " +
                    "where uc1.user.id = :userId1) and " +
                    "c.id = " +
                    "(select uc1.chat.id " +
                    "from UserChat uc1 " +
                    "where uc1.user.id = :userId2)");


        query.setParameter("userId1", userId1);
        query.setParameter("userId2", userId2);

        Chat chat;

        try {
            chat = query.getSingleResult();
        } catch (NoResultException e) {
           return null;
        }
        return chat;
    }
}
