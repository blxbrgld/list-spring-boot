package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.dao.hibernate.PublisherDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Publisher Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherDao publisherDao;

    @Autowired
    private ItemDao itemDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void persistOrMergePublisher(Publisher publisher) {
        publisherDao.persistOrMerge(publisher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Publisher> getPublishers(String attribute, Order order) {
        return publisherDao.getAll(attribute, order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Publisher> getPublisher(Integer id) {
        return publisherDao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Publisher> getPublisher(String title) {
        return publisherDao.getByTitle(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePublisher(Integer id) {
        publisherDao.deleteById(id);
    }
}
