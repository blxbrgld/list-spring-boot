package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
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
 * Activity Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "Activities")
@JsonPropertyOrder({ "id", "title", "dateUpdated" })
public class Activity implements Serializable {

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
	
	@OneToMany(mappedBy = "idActivity")
	@JsonIgnore //TODO Review This
	@ApiModelProperty(hidden = true)
	private List<ArtistActivityItem> artistActivityItems;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(dataType = "java.lang.String", readOnly = true, position = 2)
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
		Activity rhs = (Activity) obj;
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
		return new HashCodeBuilder(5, 7)
			.append(id)
			.append(title)
			.toHashCode();
	}
}