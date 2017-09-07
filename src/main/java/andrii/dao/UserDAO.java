package andrii.dao;

import andrii.entities.User;
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
    public List<User> getObjects() {
        return getSession().createQuery("from User")
                .list();
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(User user) {
        getSession().delete(user);
    }

}
