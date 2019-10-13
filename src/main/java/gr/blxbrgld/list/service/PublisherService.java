package gr.blxbrgld.list.service;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Publisher;

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
     */
    void persistOrMergePublisher(Publisher publisher);

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
     * Get publisher by title
     * @param title The title
     * @return {@link Publisher}
     */
    Optional<Publisher> getPublisher(String title);

    /**
     * Delete Publisher With The Given Id If There Are No Related Item Objects
     * @param id Publisher's Id
     */
    void deletePublisher(Integer id);
}
