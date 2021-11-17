package spring.dao.impl;

import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import spring.dao.AbstractDao;
import spring.dao.RoleDao;
import spring.exception.DataProcessingException;
import spring.model.Role;
import spring.model.RoleType;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getRoleByName(RoleType roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getRoleByNameQuery = session.createQuery("FROM Role "
                    + "WHERE roleName =: roleName", Role.class);
            getRoleByNameQuery.setParameter("roleName", roleName);
            return getRoleByNameQuery.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Can't found role with name " + roleName, e);
        }
    }
}
