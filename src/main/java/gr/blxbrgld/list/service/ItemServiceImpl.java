package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Item's Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	private static final List<String> filmCategories = new ArrayList<>(Arrays.asList(new String[] {"DVD Films", "DivX Films"}));

	private static final String booksCategory = "Books";
	
	@Autowired
	private ItemDao itemDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistItem(Item item, Errors errors) {
		validateArtistActivityItems(item, errors);
		validateCommentItems(item, errors);
		validateSubtitles(item, errors);
		validateYear(item, errors);
		validatePublisher(item, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            itemDao.persist(item);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void mergeItem(Item item, Errors errors) {
		validateArtistActivityItems(item, errors);
		validateCommentItems(item, errors);
		validateSubtitles(item, errors);
		validateYear(item, errors);
		validatePublisher(item, errors);
		boolean valid = !errors.hasErrors();
		if(valid) {
            itemDao.merge(item);
        }
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Item> getItems(String attribute, Order order, int first, int size) {
		return itemDao.getAll(attribute, order, first, size);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Item> getItemsHavingCategory(Category category) {
		return itemDao.findByCategory(category);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Item> getItemsHavingArtist(Artist artist, int first, int size) {
		return itemDao.findByArtist(artist, first, size);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public ImmutablePair<Integer, List<Item>> searchItems(String searchFor, String searchIn, String attribute, Order order, int first, int size) {
		return itemDao.search(searchFor, searchIn, attribute, order, first, size);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void initializeLucene(boolean synchronously) {
		itemDao.lucene(synchronously);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Item> getItem(Integer id) {
		return itemDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteItem(Item item) {
		itemDao.delete(item);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countItems() {
		return itemDao.count(); 
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Long countItemsHavingCategory(String title) {
		return itemDao.countItems(title);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Item findLastDateHavingCategory(String title, boolean parent) {
		return itemDao.findLastDate(title, parent);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Integer findNextPlaceHavingParent(String parent) {
		return itemDao.findNextPlace(parent);
	}
	
	/**
	 * Validate Uniqueness Of Item's artistActivityItems. At Least One artistActivityItem Is Required
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validateArtistActivityItems(Item item, Errors errors) {
		List<ArtistActivityItem> artistActivityItems = item.getArtistActivityItems();
		if(artistActivityItems.isEmpty()) { //At Least One Is Required
			errors.rejectValue("artistActivityItems", "error.missing.item.artistActivityItems");
		}
		else { //No Duplicates Allowed
			Map<String, List<String>> artistActivityMap = new HashMap<>();
			for(ArtistActivityItem artistActivityItem : artistActivityItems) {
				String artist = artistActivityItem.getIdArtist().getTitle();
				String activity = artistActivityItem.getIdActivity().getTitle();
				if("".equals(artist) || activity==null) {
					errors.rejectValue("artistActivityItems", "error.imperfect.item.artistActivityItems");
					return; //Stop Processing
				}
                else if(artistActivityMap.containsKey(activity)) { //Artists With This Activity Exist
                    List<String> artistList = artistActivityMap.get(activity);
                    artistList.add(artist);
                    artistActivityMap.put(activity, artistList);
                }
                else {
                    List<String> artistList = new ArrayList<>();
                    artistList.add(artist);
                    artistActivityMap.put(activity, artistList);
                }
			}

			for(List<String> value : artistActivityMap.values()) { //Loop Through Map Values To Check For Duplicates
				Set<String> artistSet = new HashSet<>(value);
				if(value.size() != artistSet.size()) {
					errors.rejectValue("artistActivityItems", "error.duplicate.item.artistActivityItems");
					break; //Stop Processing
				}
			}
		}
	}

	/**
	 * Validate Uniqueness Of Item's commentItems
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validateCommentItems(Item item, Errors errors) {
		List<String> commentsList = new ArrayList<>();
		for(CommentItem comment : item.getCommentItems()) {
			commentsList.add(comment.getIdComment().getTitle());
		}
		Set<String> commentsSet = new HashSet<>(commentsList);
		if(commentsSet.size() != commentsList.size()) {
            errors.rejectValue("commentItems", "error.duplicate");
        }
	}
	
	/**
	 * Validate Existence Of Subtitles For Film Items
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validateSubtitles(Item item, Errors errors) {
		if(item.getCategory() != null && filmCategories.contains(item.getCategory().getTitle()) && item.getSubtitles() == null) {
			errors.rejectValue("subtitles", "error.missing.item.subtitles");
		}
	}
	
	/**
	 * Validate Existence Of Year For Film Items
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validateYear(Item item, Errors errors) {
		if(item.getCategory() != null && filmCategories.contains(item.getCategory().getTitle()) && item.getYear() == null) {
			errors.rejectValue("year", "error.missing.item.year");
		}
	}

	/**
	 * Validate Existence Of Publisher For Book Items
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validatePublisher(Item item, Errors errors) {
		if(item.getCategory()!=null && booksCategory.equalsIgnoreCase(item.getCategory().getTitle()) && item.getPublisher()==null) {
			errors.rejectValue("publisher", "error.missing.item.publisher");
		}
	}
}