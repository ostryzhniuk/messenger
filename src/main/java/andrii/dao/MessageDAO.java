package andrii.dao;

import andrii.entities.Message;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class MessageDAO extends GenericDAO<Message> {

    @Override
    public void save(Message message) {
        getSession().save(message);
    }

    @Override
    public List<Message> getObjects() {
        return null;
    }

    @Override
    public void update(Message value) {

    }

    @Override
    public void delete(Message value) {

    }

    public List<Message> getMessages(Integer chatId) {
        Query<Message> query = getSession().createQuery("from Message m " +
                "where m.chat.id = :chatId");

        query.setParameter("chatId", chatId);

        return query.getResultList();
    }
}
