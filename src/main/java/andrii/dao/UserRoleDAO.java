package andrii.dao;

import andrii.entities.UserRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRoleDAO extends GenericDAO<UserRole> {

    @Override
    public void save(UserRole userRole) {
        getSession().save(userRole);
    }

    @Override
    public List<UserRole> getObjects() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(UserRole userRole) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(UserRole userRole) {
        throw new UnsupportedOperationException();
    }

}

