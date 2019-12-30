package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gr.blxbrgld.list.jackson.CategoryDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

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
@NoArgsConstructor
@Entity
@Table(name = "Categories")
@NamedQuery(name = "findCategoriesByParent", query = "FROM Category WHERE parent = :parent")
@JsonPropertyOrder({ "id", "title", "categories", "dateUpdated" })
@JsonDeserialize(using = CategoryDeserializer.class)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@ApiModelProperty(readOnly = true, position = 0)
	private Integer id;

	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Title")
	@ApiModelProperty(required = true, allowableValues = "range[3, 45]", position = 1)
	private String title;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "Parent", referencedColumnName = "Id")
	@ApiModelProperty(dataType = "java.lang.String", position = 2)
	private Category parent;

	@JsonManagedReference
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(mappedBy = "parent")
	@ApiModelProperty(readOnly = true, position = 3)
	private List<Category> categories;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Calendar dateUpdated;

	/**
	 * Category builder
	 * @param title The title
	 * @param parent The parent
	 */
	@Builder
	public Category(String title, String parent) {
		this.title = title;
		if(StringUtils.trimToNull(parent)!=null) {
			this.parent = Category.builder().title(parent).build();
		}
	}

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