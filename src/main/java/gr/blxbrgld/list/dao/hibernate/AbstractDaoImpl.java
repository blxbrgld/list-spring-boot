package gr.blxbrgld.list.dao.hibernate;

import gr.blxbrgld.list.enums.Order;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.util.ReflectionUtils;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Abstract DAO Implementation
 * @param <T> Generic Class
 * @author blxbrgld
 */
@Slf4j
public abstract class AbstractDaoImpl<T> implements AbstractDao<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	private Class<T> domainClass;
	
	/**
	 * Protected Method Allowing Subclasses To Perform Persistent Operations Against The Hibernate Session
	 * @return Hibernate Session
	 */
	protected Session getSession() {
		return entityManager.unwrap(Session.class).getSession();
	}
	
	/**
	 * Reflection To Get Actual Domain Class
	 * @return Domain Class
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getDomainClass() {
		if(domainClass==null) {
			ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
			this.domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}
	
	/**
	 * Reflection To Get Actual Domain Class Name
	 * @return Domain Class Name
	 */
	private String getDomainClassName() {
		return getDomainClass().getName();
	}
	
	/**
     * {@inheritDoc}
	 */
	@Override
	public void persist(T t) {
        setDateUpdated(t);
		entityManager.persist(t);
		entityManager.flush(); // Flush To Get Id Of Object Persisted //TODO Is This Needed?
	}
	
	/**
     * {@inheritDoc}
	 */
	@Override
	public void merge(T t) {
		setDateUpdated(t);
		entityManager.merge(t);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persistOrMerge(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "getId");
		if(method != null) {
			try {
				if(method.invoke(t) == null) {
					persist(t);
				} else {
					merge(t);
				}
			} catch(Exception exception) {
				log.error("Exception", exception);
			}
		}
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<T> get(Serializable id) {
		return Optional.ofNullable(getSession().get(getDomainClass(), id));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<T> getByTitle(String title) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(getDomainClass());
		Root<T> root = criteria.from(getDomainClass());
		criteria.select(root).where(builder.equal(root.get("title"), title));
		Query query = entityManager.createQuery(criteria);
		try {
			//noinspection unchecked
			return Optional.of((T) query.getSingleResult());
		} catch (NoResultException exception) {
			return Optional.empty();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getAll(String attribute, Order order) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(getDomainClass());
		Root<T> root = criteria.from(getDomainClass());
		if(Order.DESC.equals(order)) {
			criteria.orderBy(builder.desc(root.get(attribute)));
		} else if(attribute != null) {
			criteria.orderBy(builder.asc(root.get(attribute)));
		}
		return entityManager.createQuery(criteria).getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<T> getAll(String attribute, Order order, int first, int size) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(getDomainClass());
		Root<T> root = criteria.from(getDomainClass());
		if(Order.DESC.equals(order)) {
			criteria.orderBy(builder.desc(root.get(attribute)));
		} else if(attribute != null) {
			criteria.orderBy(builder.asc(root.get(attribute)));
		}
		TypedQuery<T> typedQuery = entityManager.createQuery(criteria);
		typedQuery.setFirstResult(first);
		typedQuery.setMaxResults(size);
		return entityManager.createQuery(criteria).getResultList();
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public void delete(T t) {
		getSession().delete(t);
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public void deleteById(Serializable id) {
		get(id).ifPresent(this::delete);
	}

    /**
     * {@inheritDoc}
     */
    @Override
	public Long count() {
		return (Long) entityManager
			.createQuery("SELECT COUNT(*) FROM " + getDomainClassName())
			.getSingleResult();
	}

	/**
	 * Set Object's Date Updated Before Persisting Or Merging
	 * @param t The Object
	 */
	private void setDateUpdated(T t) {
		Method method = ReflectionUtils.findMethod(getDomainClass(), "setDateUpdated", Calendar.class);
		if(method!=null) {
			try {
				method.invoke(t, Calendar.getInstance());
			} catch(Exception exception) {
				log.error("Exception", exception);
			}
		}
	}
}