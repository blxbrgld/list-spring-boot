package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
 * ArtistActivityItem Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "ArtistsActivitiesItems")
@NamedQueries({
	@NamedQuery(name = "findArtistActivityItemsByArtist", query = "FROM ArtistActivityItem WHERE idArtist = :artist"),
	@NamedQuery(name = "findArtistActivityItemsByActivity", query = "FROM ArtistActivityItem WHERE idActivity = :activity"),
	@NamedQuery(name = "deleteArtistActivityItemByItem", query = "DELETE FROM ArtistActivityItem WHERE idItem = :item"),
	@NamedQuery(name = "countItemsHavingArtist", query = "SELECT COUNT(*) FROM ArtistActivityItem WHERE idArtist = :artist"),
})
public class ArtistActivityItem implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Integer id;

	//@IndexedEmbedded //TODO Hibernate Search
	@ManyToOne
	@JoinColumn(name = "IdArtist", referencedColumnName = "Id", nullable = false)
	@JsonProperty("artist")
	@ApiModelProperty(name = "artist", dataType = "java.lang.String", position = 0)
	private Artist idArtist;
	
	//@ContainedIn //TODO Hibernate Search
	@ManyToOne
	@JoinColumn(name = "IdItem", referencedColumnName = "Id", nullable = false)
	@JsonBackReference
	@ApiModelProperty(hidden = true)
	private Item idItem;
	
	@ManyToOne
	@JoinColumn(name = "IdActivity", referencedColumnName = "Id", nullable = false)
	@JsonProperty("activity")
	@ApiModelProperty(name = "activity", dataType = "java.lang.String", position = 1)
	private Activity idActivity;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Calendar dateUpdated;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("idArtist", idArtist)
			.append("idItem", idItem)
			.append("idActivity", idActivity)
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
		ArtistActivityItem rhs = (ArtistActivityItem) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(idArtist, rhs.idArtist)
			.append(idItem, rhs.idItem)
			.append(idActivity, rhs.idActivity)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 19)
			.append(id)
			.append(idArtist)
			.append(idItem)
			.append(idActivity)
			.toHashCode();
	}
}
