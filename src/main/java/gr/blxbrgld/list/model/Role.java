package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

/**
 * Role Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Roles")
@JsonPropertyOrder({ "id", "title", "dateUpdated" })
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@ApiModelProperty(readOnly = true, position = 0)
	private Integer id;

	@NotNull
	@Length(min = 3, max = 15)
	@Column(name = "Title")
	@ApiModelProperty(required = true, allowableValues = "range[3, 15]", position = 1)
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Calendar dateUpdated;

	/**
	 * Role builder
	 * @param title The title
	 */
	@Builder
	public Role(String title) {
		this.title = title;
	}

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
		Role rhs = (Role) obj;
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
		return new HashCodeBuilder(47, 53)
			.append(id)
			.append(title)
			.toHashCode();
	}
}