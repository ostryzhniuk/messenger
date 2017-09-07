package andrii.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public abstract class GenericDAO<E> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public abstract void save(E value);

    public abstract List<E> getObjects();

    public abstract void update(E value);

    public abstract void delete(E value);

    protected EntityManager getEntityManager() {
        return getSession().getEntityManagerFactory().createEntityManager();
    }
}
