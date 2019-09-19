package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.List;

/**
 * Artist Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Artists")
@NamedQueries({
	@NamedQuery(name = "findLastArtist", query = "FROM Artist ORDER BY dateUpdated DESC"),
	@NamedQuery(name = "findArtistsLike", query = "FROM Artist WHERE title LIKE :term ORDER BY title ASC"),
	@NamedQuery(name = "randomArtist", query = "FROM Artist ORDER BY RAND()")
})
//TODO Hibernate Search
/*
@AnalyzerDef(
	name = "artistAnalyzer",
	charFilters = {
		@CharFilterDef(factory = HTMLStripCharFilterFactory.class)
	},
	tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
	filters = {
		@TokenFilterDef(factory = StandardFilterFactory.class),
		@TokenFilterDef(factory = LowerCaseFilterFactory.class),
		@TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
			@Parameter(name = "encoder", value = "Metaphone")
		})
	}
)
@Analyzer(definition = "artistAnalyzer")
*/
@JsonPropertyOrder({ "id", "title", "description", "dateUpdated" })
public class Artist implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@ApiModelProperty(readOnly = true, position = 0)
	private Integer id;

	//@Field //TODO Hibernate Search
	@NotNull
	@Length(min = 3, max = 255)
	@Column(name = "Title")
	@ApiModelProperty(required = true, allowableValues = "range[3, 255]", position = 1)
	private String title;
	
	//@Field //TODO Hibernate Search
	@Lob
	@Column(name = "Description", columnDefinition = "text")
	@ApiModelProperty(position = 2)
	private String description;
	
	//@ContainedIn //TODO Hibernate Search
	@OneToMany(mappedBy = "idArtist")
	@JsonIgnore //TODO Review This
	@ApiModelProperty(hidden = true)
	private List<ArtistActivityItem> artistActivityItems;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(dataType = "java.lang.String", readOnly = true, position = 3)
	private Calendar dateUpdated;

	/**
	 * Artist builder
	 * @param title The title
	 */
	@Builder
	public Artist(String title) {
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
			.append("description", description)
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
		Artist rhs = (Artist) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(title, rhs.title)
			.append(description, rhs.description)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(11, 13)
			.append(id)
			.append(title)
			.append(description)
			.toHashCode();
	}
}
