package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.SystemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * System service implementation
 * @author blxbrgld
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SystemDao systemDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeLucene(boolean synchronously) {
        systemDao.lucene(synchronously);
    }
}
