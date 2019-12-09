package gr.blxbrgld.list.dao.hibernate;

/**
 * System DAO interface
 * @author blxbrgld
 */
public interface SystemDao {

    /**
     * Re-Build Lucene Index From Scratch
     * @param synchronously Index Synchronously or Asynchronously
     */
    void lucene(boolean synchronously);
}
