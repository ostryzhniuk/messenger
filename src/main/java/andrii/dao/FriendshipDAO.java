package andrii.dao;

import andrii.entities.Friendship;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class FriendshipDAO extends GenericDAO<Friendship> {

    @Override
    public void save(Friendship friendship) {
        getSession().save(friendship);
    }

    @Override
    public Friendship get(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Friendship friendship) {
        getSession().update(friendship);
    }

    @Override
    public void delete(Friendship friendship) {
        getSession().delete(friendship);
    }
}
