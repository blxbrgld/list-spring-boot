package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.FixtureType;
import gr.blxbrgld.list.model.Fixture;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Fixture DAO Implementation
 * @author blxbrgld
 */
@Repository
public class FixtureDaoImpl  extends AbstractDaoImpl<Fixture> implements FixtureDao {

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Fixture> getByType(FixtureType type) {
        Query query = getSession().getNamedQuery("getFixturesByType");
        query.setParameter("type", type);
        return (List<Fixture>) query.list();
    }
}
