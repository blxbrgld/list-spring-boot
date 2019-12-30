package gr.blxbrgld.list.dao.hibernate;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * System DAO implementation
 * @author blxbrgld
 */
@Component
public class SystemDaoImpl implements SystemDao {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void lucene(boolean synchronously) { //TODO Hibernate Search
		/*
		FullTextSession fullTextSession = Search.getFullTextSession(entityManager.unwrap(Session.class).getSession());
		if(synchronously) {
            try {
                fullTextSession.createIndexer().startAndWait();
            } catch(Exception exception) {
                log.error("Exception", exception);
            }
        } else {
			fullTextSession.createIndexer().start();
		}
		*/
    }
}
