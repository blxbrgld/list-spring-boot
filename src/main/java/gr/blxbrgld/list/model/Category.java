package gr.blxbrgld.list.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Category Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "Categories")
@NamedQuery(name = "findCategoriesByParent", query = "FROM Category WHERE parent = :parent")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	private Integer id;

	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "Parent", referencedColumnName = "Id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> categories;
	
	@OneToMany(mappedBy = "category")
	private List<Item> items;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DateUpdated")
	private Calendar dateUpdated;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("title", title)
			.append("parent", parent)
			.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null) { return false; }
		if(obj==this) { return true; }
		if(obj.getClass()!=getClass()) {
			return false;
		}
		Category rhs = (Category) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(title, rhs.title)
			.append(parent, rhs.parent)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(23, 29)
			.append(id)
			.append(title)
			.append(parent)
			.toHashCode();
	}
}