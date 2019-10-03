package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.dao.hibernate.ItemDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.*;
import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ArtistService artistService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private PublisherService publisherService;

	@Autowired
	private SubtitlesService subtitlesService;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeItem(Item item) {
		//validateArtistActivityItems(item, errors); //TODO Validator
		//validateCommentItems(item, errors); //TODO Validator
		//validateSubtitles(item, errors); //TODO Validator
		//validateYear(item, errors); //TODO Validator
		//validatePublisher(item, errors); //TODO Validator

		// All related items are detached when we get here, we have to handle the relationships
		attachCategory(item);
		attachPublisher(item);
		attachSubtitles(item);
		attachArtists(item);
		attachComments(item);
		itemDao.persistOrMerge(item);
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
	public Optional<Item> getItem(String titleEng) {
		return itemDao.getByTitleEng(titleEng);
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
	public void deleteItem(Integer id) {
		itemDao.deleteById(id);
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
	 * Validate Uniqueness Of Item's artistActivityItems. At Least One artistActivityItem Is Required
	 * @param item Item Object
	 * @param errors BindingResult Errors Of Item Form
	 */
	private void validateArtistActivityItems(Item item, Errors errors) { //TODO to be deleted
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
	 */
	private void validateCommentItems(Item item) { //TODO to be deleted
		List<String> commentsList = new ArrayList<>();
		for(CommentItem comment : item.getCommentItems()) {
			commentsList.add(comment.getIdComment().getTitle());
		}
		Set<String> commentsSet = new HashSet<>(commentsList);
		if(commentsSet.size() != commentsList.size()) {
        }
	}
	
	/**
	 * Validate Existence Of Subtitles For Film Items
	 * @param item Item Object
	 */
	private void validateSubtitles(Item item) { //TODO to be deleted
		if(item.getCategory() != null && filmCategories.contains(item.getCategory().getTitle()) && item.getSubtitles() == null) {}
	}
	
	/**
	 * Validate Existence Of Year For Film Items
	 * @param item Item Object
	 */
	private void validateYear(Item item) { //TODO to be deleted
		if(item.getCategory() != null && filmCategories.contains(item.getCategory().getTitle()) && item.getYear() == null) {}
	}

	/**
	 * Validate Existence Of Publisher For Book Items
	 * @param item Item Object
	 */
	private void validatePublisher(Item item) { //TODO to be deleted
		if(item.getCategory()!=null && booksCategory.equalsIgnoreCase(item.getCategory().getTitle()) && item.getPublisher()==null) {}
	}

	/**
	 * Attach the detached category to the given item. The {@link Category} has already been validated for existence
	 * @param item {@link Item}
	 */
	private void attachCategory(Item item) {
		if(item.getCategory()!=null && StringUtils.trimToNull(item.getCategory().getTitle())!=null) { // Sanity check, category would be always present
			//noinspection ConstantConditions
			item.setCategory(categoryService.getCategory(item.getCategory().getTitle()).get());
		}
	}

	/**
	 * Attach the detached publisher to the given item. The {@link Publisher} has already been validated for existence
	 * @param item {@link Item}
	 */
	private void attachPublisher(Item item) {
		if(item.getPublisher()!=null && StringUtils.trimToNull(item.getPublisher().getTitle())!=null) {
			//noinspection ConstantConditions
			item.setPublisher(publisherService.getPublisher(item.getPublisher().getTitle()).get());
		}
	}

	/**
	 * Attach the detached subtitle to the given item. The {@link Subtitles} has already been validated for existence
	 * @param item {@link Item}
	 */
	private void attachSubtitles(Item item) {
		if(item.getSubtitles()!=null && StringUtils.trimToNull(item.getSubtitles().getTitle())!=null) {
			//noinspection ConstantConditions
			item.setSubtitles(subtitlesService.getSubtitles(item.getSubtitles().getTitle()).get());
		}
	}

	/**
	 * Attach the detached artist/activity items to the given item. The {@link Activity} already exist, the {@link Artist} may not
	 * exist but are created here and the {@link ArtistActivityItem} may not and left as a task the owning {@link Item} has to handle
	 * @param item {@link Item}
	 */
	private void attachArtists(Item item) {
		List<ArtistActivityItem> artistActivityItems = new ArrayList<>();
		for(ArtistActivityItem relation : item.getArtistActivityItems()) {
			if(relation.getIdArtist()!=null && StringUtils.trimToNull(relation.getIdArtist().getTitle())!=null
				&& relation.getIdActivity()!=null && StringUtils.trimToNull(relation.getIdActivity().getTitle())!=null) {
				ArtistActivityItem artistActivityItem = null;
				if(item.getId()!=null) { // Check if relation already exist in database for an existing object
					Optional<Item> existing = itemDao.get(item.getId());
					if(existing.isPresent()) {
						for (ArtistActivityItem current : existing.get().getArtistActivityItems()) {
							if (relation.getIdArtist().getTitle().equals(current.getIdArtist().getTitle()) && relation.getIdActivity().getTitle().equals(current.getIdActivity().getTitle())) {
								artistActivityItem = current;
							}
						}
					}
				}
				if(artistActivityItem == null) {
					artistActivityItem = new ArtistActivityItem();
					artistActivityItem.setIdItem(item);
					artistActivityItem.setIdArtist(artistService.artistExists(relation.getIdArtist().getTitle()));
					//noinspection ConstantConditions
					artistActivityItem.setIdActivity(activityService.getActivity(relation.getIdActivity().getTitle()).get());
					artistActivityItem.setDateUpdated(Calendar.getInstance());
				}
				artistActivityItems.add(artistActivityItem);
			}
		}
		item.setArtistActivityItems(artistActivityItems);
	}

	/**
	 * Attach the detached comment items to the given item. The {@link Comment} already exist but the {@link CommentItem} may not
	 * @param item {@link Item}
	 */
	private void attachComments(Item item) {
		Set<CommentItem> commentItems = new HashSet<>();
		for(CommentItem relation : item.getCommentItems()) {
			if(relation.getIdComment()!=null && StringUtils.trimToNull(relation.getIdComment().getTitle())!=null) {
				CommentItem commentItem = null;
				if(item.getId()!=null) { // Check if relation already exist in database for an existing object
					Optional<Item> existing = itemDao.get(item.getId());
					if(existing.isPresent()) {
						for(CommentItem current : existing.get().getCommentItems()) {
							if(relation.getIdComment().getTitle().equals(current.getIdComment().getTitle())) {
								commentItem = current;
							}
						}
					}
				}
				if(commentItem == null) { // Item is a new one or relation does not exist
					commentItem = new CommentItem();
					commentItem.setIdItem(item);
					//noinspection ConstantConditions
					commentItem.setIdComment(commentService.getComment(relation.getIdComment().getTitle()).get());
					commentItem.setDateUpdated(Calendar.getInstance());
				}
				commentItems.add(commentItem);
			}
		}
		item.setCommentItems(commentItems);
	}
}