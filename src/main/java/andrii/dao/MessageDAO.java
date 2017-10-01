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
    public Message get(Integer messageId) {
        Query<Message> query = getSession().createQuery("from Message m " +
                "where m.id = :messageId");

        query.setParameter("messageId", messageId);

        return query.getSingleResult();
    }

    @Override
    public void update(Message message) {
        getSession().update(message);
    }

    @Override
    public void delete(Message message) {
        getSession().delete(message);
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
                "   (select m.time " +
                "   from UserChat uc, Message m " +
                "   where uc.chat.id = :chatId and " +
                "   uc.user.id = :userId and " +
                "   uc.lastMessage.id = m.id)");

        query.setParameter("chatId", chatId);
        query.setParameter("userId", userId);

        return query.getSingleResult();
    }
}
