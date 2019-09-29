package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * CommentItem Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "CommentsItems")
@NamedQueries({
	@NamedQuery(name = "findCommentItemsByComment", query = "FROM CommentItem WHERE idComment = :comment"),
	@NamedQuery(name = "deleteCommentItemByItem", query = "DELETE FROM CommentItem WHERE idItem = :item")
})
public class CommentItem implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "IdItem", referencedColumnName = "Id", nullable = false)
	private Item idItem;
	
	@ManyToOne
	@JoinColumn(name = "IdComment", referencedColumnName = "Id", nullable = false)
	private Comment idComment;

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
			.append("idItem", idItem)
			.append("idComment", idComment)
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
		CommentItem rhs = (CommentItem) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(idItem, rhs.idItem)
			.append(idComment, rhs.idComment)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(41, 43)
			.append(id)
			.append(idItem)
			.append(idComment)
			.toHashCode();
	}
}