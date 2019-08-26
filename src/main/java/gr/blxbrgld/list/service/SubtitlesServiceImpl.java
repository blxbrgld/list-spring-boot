package gr.blxbrgld.list.service;

import gr.blxbrgld.list.dao.hibernate.SubtitlesDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Subtitles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Subtitles' Service Implementation
 * @author blxbrgld
 */
@Service
@Transactional
public class SubtitlesServiceImpl implements SubtitlesService {

	@Autowired
	private SubtitlesDao subtitlesDao;

    /**
     * {@inheritDoc}
     */
	@Override
	public void persistOrMergeSubtitles(Subtitles subtitles) {
		subtitlesDao.persistOrMerge(subtitles);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public List<Subtitles> getSubtitles(String attribute, Order order) {
		return subtitlesDao.getAll(attribute, order);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public Optional<Subtitles> getSubtitles(Integer id) {
		return subtitlesDao.get(id);
	}

    /**
     * {@inheritDoc}
     */
	@Override
	public void deleteSubtitles(Integer id) {
		subtitlesDao.deleteById(id);
	}
}
