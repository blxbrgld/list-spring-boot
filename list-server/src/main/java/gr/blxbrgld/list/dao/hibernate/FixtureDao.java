package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.FixtureType;
import gr.blxbrgld.list.model.Fixture;

import java.util.List;

/**
 * Fixture DAO Interface
 * @author blxbrgld
 */
public interface FixtureDao extends AbstractDao<Fixture> {

    /**
     * Get fixtures by fixture type
     * @param type {@link FixtureType}
     * @return List of {@link Fixture}
     */
    List<Fixture> getByType(FixtureType type);
}
