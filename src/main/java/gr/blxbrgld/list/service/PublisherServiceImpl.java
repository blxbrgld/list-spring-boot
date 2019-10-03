package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.dao.hibernate.PublisherDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

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
    public void persistPublisher(Publisher publisher, Errors errors) {
        validateTitle(publisher, errors);
        if(!errors.hasErrors()) {
            publisherDao.persist(publisher);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mergePublisher(Publisher publisher, Errors errors) {
        validateTitle(publisher, errors);
        if(!errors.hasErrors()) {
            publisherDao.merge(publisher);
        }
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
    public boolean deletePublisher(Integer id) {
        Optional<Publisher> publisher = publisherDao.get(id);
        if(publisher.isPresent() && !itemDao.havingPublisherExists(publisher.get())) { // No Items With This Publisher Exist
            publisherDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate Uniqueness Of Publisher's Title
     * @param publisher Publisher Object
     * @param errors BindingResult Errors Of Subtitles Form
     */
    private void validateTitle(Publisher publisher, Errors errors) {
        Optional<Publisher> existing = publisherDao.getByTitle(publisher.getTitle());
        if(existing.isPresent() && !existing.get().getId().equals(publisher.getId()) ) {
            errors.rejectValue("title", "error.duplicate");
        }
    }
}
