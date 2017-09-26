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

    public Long getUnreadMessages(Integer chatId, Integer userId) {
        Query<Long> query = getSession().createQuery("select count(m) " +
                "from Chat c, Message m " +
                "where c.id = :chatId and " +
                "c.id = m.chat.id and " +
                "m.user.id != :userId and " +
                "m.time > " +
                "   (select uc.lastVisit " +
                "   from UserChat uc " +
                "   where uc.chat.id = :chatId and " +
                "   uc.user.id = :userId)");

        query.setParameter("chatId", chatId);
        query.setParameter("userId", userId);

        return query.getSingleResult();
    }
}
