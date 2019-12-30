package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * Item's DAO Implementation
 * @author blxbrgld
 */
@Slf4j
@Repository
@SuppressWarnings("JpaQueryApiInspection")
public class ItemDaoImpl extends AbstractDaoImpl<Item> implements ItemDao {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Item> getByTitleEng(String titleEng) {
		Query query = getSession().getNamedQuery("findItemByTitleEng");
		query.setParameter("titleEng", titleEng);
		try {
			//noinspection unchecked
			return Optional.of((Item) query.getSingleResult());
		} catch (NoResultException exception) {
			return Optional.empty();
		}
	}

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
    @Override
	public List<Item> findByCategory(Category category) {
		Query query = getSession().getNamedQuery("findItemsByCategory");
		query.setParameter("category", category);
		return (List<Item>) query.list();
	}

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findByArtist(Artist artist, int first, int size) {
		Query query = getSession().getNamedQuery("findItemsByArtist");
		query.setParameter("artist", artist);
		query.setFirstResult(first);
		query.setMaxResults(size);
		return (List<Item>) query.list();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	@SuppressWarnings("unchecked")
	public ImmutablePair<Integer, List<Item>> search(String searchFor, String searchIn, String attribute, Order order, int first, int size) { //TODO Hibernate Search
		/*
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Item.class).get();
		org.apache.lucene.search.Query luceneQuery;
		String searchString = searchFor != null ? searchFor.trim() : "";
		
		if(searchIn != null && "*".equals(searchString)) { // All Category Items
			luceneQuery = queryBuilder
                .keyword()
                .wildcard()
                .onField("titleEng")
                .matching(searchString)
				.createQuery();
		} else if(searchIn != null && "Artist".equals(searchIn)) { //Search Only Artists
			luceneQuery = queryBuilder
				.phrase()
                .onField("artistActivityItems.idArtist.title").boostedTo(2)
                .andField("artistActivityItems.idArtist.description")
                .sentence(searchString)
				.createQuery();
		} else { // Search All
			luceneQuery = queryBuilder
                .phrase()
                .onField("titleEng").boostedTo(2)
                    .andField("titleEll")
                    .andField("description")
                    .andField("artistActivityItems.idArtist.title").boostedTo(2)
                    .andField("artistActivityItems.idArtist.description")
                .sentence(searchString)
                .createQuery();
		}
		
		FullTextQuery hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Item.class);

		//
		// Filters
		//
		if(searchIn != null) {
			if("*".equals(searchString)) {
				String[] tokens = StringUtils.tokenizeToStringArray(searchIn, " ");
				if("music".equalsIgnoreCase(tokens[0]) || "films".equalsIgnoreCase(tokens[0])) { // Parent Category Filter
					hibernateQuery.enableFullTextFilter("parentCategoryFilter").setParameter("parentCategory", tokens[0].toLowerCase());
				} else { // Category Filter
					hibernateQuery.enableFullTextFilter("categoryFilter").setParameter("category", tokens[0].toLowerCase());
				}
			} else if(!"Artist".equals(searchIn)) { // Parent Category Filter
				String parentCategory = "Music".equals(searchIn) ? "music" : "Films".equals(searchIn) ? "films" : "books";
				hibernateQuery.enableFullTextFilter("parentCategoryFilter").setParameter("parentCategory", parentCategory);
			}
		}
		
		//
		// Order By Clause
		//
		boolean ordering = order != null && "DESC".equals(order);
		if(property != null && "titleEng".equals(property)) {
			Sort sort = new Sort(new SortField("sortTitle", SortField.STRING, ordering));
			hibernateQuery.setSort(sort);
		} else if(property != null && "artist".equals(property)) {
			Sort sort = new Sort(new SortField("sortArtist", SortField.STRING, ordering));
			hibernateQuery.setSort(sort);
		}
		
		int noOfResults = hibernateQuery.getResultSize();
		hibernateQuery.setFirstResult(first);
		hibernateQuery.setMaxResults(size);
		
		return new ImmutablePair<>(noOfResults, (List<Item>) hibernateQuery.list());
		*/
		return null;
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingCategoryExists(Category category) {
		Query query = getSession().getNamedQuery("findItemsByCategory");
		query.setParameter("category", category);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean havingSubtitlesExists(Subtitles subtitles) {
		Query query = getSession().getNamedQuery("findItemsBySubtitles");
		query.setParameter("subtitles", subtitles);
		query.setMaxResults(1);
        return !query.list().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean havingPublisherExists(Publisher publisher) {
		Query query = getSession().getNamedQuery("findItemsByPublisher");
		query.setParameter("publisher", publisher);
		query.setMaxResults(1);
		return !query.list().isEmpty();
	}


	/**
     * {@inheritDoc}
     */
	@Override
	public Long countItems(String title) {
		Query query = getSession().getNamedQuery("countItemsHavingCategory");
		query.setParameter("title", title);
		return (Long) query.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteByTitleEng(String titleEng) {
		Optional<Item> item = getByTitleEng(titleEng);
		if(item.isPresent()) {
			delete(item.get());
		} else {
			// It's perfectly valid for requests generated by fixtures and specifically when we test the error 400 cases
			log.warn("Delete of item with title {} requested but the item does not exist.", titleEng);
		}
	}
}
