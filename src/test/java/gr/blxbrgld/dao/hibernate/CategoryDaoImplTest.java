package gr.blxbrgld.dao.hibernate;

import gr.blxbrgld.list.dao.hibernate.CategoryDao;
import gr.blxbrgld.list.enums.Order;
import gr.blxbrgld.list.model.Category;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CategoryDaoImpl Tests
 * @author blxbrgld
 */
@Ignore
public class CategoryDaoImplTest { //TODO To Be Deleted

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void getAll() {
        List<Category> categoryList = categoryDao.getAll("title", null); //Ascending Order Is The Default
        assertEquals("Classical Music", categoryList.get(0).getTitle());
        categoryList = categoryDao.getAll("title", Order.DESC);
        assertEquals("Popular Music", categoryList.get(0).getTitle());
    }

    @Test
    public void findByTitle() {
        assertTrue(categoryDao.getByTitle("Greek Music").isPresent());
    }

    @Test
    public void findByParent() {
        List<Category> list = categoryDao.findByParent(categoryDao.getByTitle("Music").get());
        assertEquals(3, list.size());
    }
}
