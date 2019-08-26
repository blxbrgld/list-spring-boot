package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Publisher;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;

/**
 * Publisher Service Interface
 * @author blxbrgld
 */
public interface PublisherService {

    /**
     * Persist Publisher Object
     * @param publisher Publisher Object
     * @param errors BindingResult Errors Of Publisher Form
     */
    void persistPublisher(Publisher publisher, Errors errors);

    /**
     * Merge Publisher Object
     * @param publisher Publisher Object
     * @param errors BindingResult Errors Of Publisher Form
     */
    void mergePublisher(Publisher publisher, Errors errors);

    /**
     * Get All Publisher Objects
     * @param attribute Attribute To Order Results By
     * @param order Ascending or Descending Ordering
     * @return List of Publishers
     */
    List<Publisher> getPublishers(String attribute, Order order);

    /**
     * Get Publisher Object Given It's Id
     * @param id Publisher's Id
     * @return Publisher Object
     */
    Optional<Publisher> getPublisher(Integer id);

    /**
     * Delete Publisher With The Given Id If There Are No Related Item Objects
     * @param id Publisher's Id
     * @return TRUE or FALSE
     */
    boolean deletePublisher(Integer id);
}
