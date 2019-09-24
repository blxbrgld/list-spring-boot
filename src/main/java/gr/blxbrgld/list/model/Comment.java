package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
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
 * Comment Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "Comments")
@NamedQuery(name = "findCommentByTitle", query = "FROM Comment WHERE title = :title")
@JsonPropertyOrder({ "id", "title", "dateUpdated" })
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	private Integer id;

	@NotNull
	@Length(min = 1, max = 45)
	@Column(name = "Title")
	private String title;
	
	@OneToMany(mappedBy = "idComment")
	@JsonIgnore //TODO Review This
	private List<CommentItem> commentItems;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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
		Comment rhs = (Comment) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(title, rhs.title)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(31, 37)
			.append(id)
			.append(title)
			.toHashCode();
	}
}