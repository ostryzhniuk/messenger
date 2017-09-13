package andrii.dao;

import andrii.entities.Chat;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Query<Chat> query = getSession().createQuery("from User " +
                "where email = :userId1");
        query.setParameter("userId1", userId1);
//        query.setParameter("userId2", userId2);
        return query.getSingleResult();
    }
}
