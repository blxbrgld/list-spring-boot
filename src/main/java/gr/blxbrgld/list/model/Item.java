package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import gr.blxbrgld.list.validators.FileValid;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Item Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "Items")
@NamedQueries({
	@NamedQuery(name = "findItemsByCategory", query = "FROM Item WHERE category = :category OR category.parent = :category"),
	@NamedQuery(name = "findItemsByArtist", query = "SELECT DISTINCT i FROM Item i, IN (i.artistActivityItems) c WHERE c.idArtist = :artist ORDER BY i.titleEng"),
	@NamedQuery(name = "findItemsBySubtitles", query = "FROM Item WHERE subtitles = :subtitles"),
	@NamedQuery(name = "findItemsByPublisher", query = "FROM Item WHERE publisher = :publisher"),
	@NamedQuery(name = "countItemsHavingCategory", query = "SELECT COUNT(*) FROM Item WHERE category.title = :title"),
	@NamedQuery(name = "findLastItemHavingCategory", query = "FROM Item WHERE category.title = :title ORDER BY dateUpdated DESC"),
	@NamedQuery(name = "findLastItemHavingParentCategory", query = "FROM Item WHERE category.parent.title = :title ORDER BY dateUpdated DESC"),
	@NamedQuery(name = "findNextPlaceHavingParent", query = "SELECT max(place) + 1 FROM Item WHERE category.parent.title = :parent")
})
//TODO Hibernate Search
/*
@Indexed
@AnalyzerDef(
	name = "itemAnalyzer",
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
@Analyzer(definition = "itemAnalyzer")
@FullTextFilterDefs({
	@FullTextFilterDef(name = "parentCategoryFilter", impl = ParentCategoryFilterFactory.class),
	@FullTextFilterDef(name = "categoryFilter", impl = CategoryFilterFactory.class),
})
@ClassBridges({
	@ClassBridge(name = "itemParentCategory", impl = ParentCategoryBridge.class),
	@ClassBridge(name = "itemCategory", impl = CategoryBridge.class)
})
*/
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	private Integer id;

	//TODO Hibernate Search
	/*
	@Fields({
		@Field,
		@Field(name = "sortTitle", analyze = Analyze.NO)
	})
	*/
	@NotNull
	@Length(min = 1, max = 255)
	@Column(name = "TitleEng")
	private String titleEng;
	
	//@Field //TODO Hibernate Search
	@Length(max = 255)
	@Column(name = "TitleEll")
	private String titleEll;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "Category", referencedColumnName = "Id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "Publisher", referencedColumnName = "Id")
	private Publisher publisher;
	
	@Length(max = 45)
	@Column(name = "photoPath")
	private String photoPath;

	@Transient
	@FileValid(message = "{gr.blxbrgld.list.validators.FileValid.message}")
	private CommonsMultipartFile photo;
	
	//@Field //TODO Hibernate Search
	@Lob
	@Column(name = "Description", columnDefinition = "text")
	private String description;
	
	@Range(min = 1900, max = 2100)
	@Column(name = "Year")
	private Integer year;
	
	//@Field //TODO Hibernate Search
	//@NumericField //TODO Hibernate Search
	@Column(name = "Rating")
	private Integer rating;
	
	@ManyToOne
	@JoinColumn(name = "Subtitles")
	private Subtitles subtitles;
	
	@Range(min = 1, max = 100)
	@Column(name = "Discs")
	private Integer discs;
	
	@Min(1)
	@Column(name = "Place")
	private Integer place;

	@Min(10)
	@Column(name = "Pages")
	private Integer pages;
	
	//@Field(name = "sortArtist", analyze = Analyze.NO) //TODO Hibernate Search
	//@FieldBridge(impl = ArtistActivityItemBridge.class) //TODO Hibernate Search
	//@IndexedEmbedded //TODO Hibernate Search
	@OneToMany(mappedBy = "idItem", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
	@OrderBy(clause = "id ASC")
	@Fetch(FetchMode.SELECT)
	private List<ArtistActivityItem> artistActivityItems;
	
	@OneToMany(mappedBy = "idItem", fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE })
	@OrderBy(clause = "id ASC")
	@Fetch(FetchMode.SELECT)
	private Set<CommentItem> commentItems;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "DateUpdated")
	private Calendar dateUpdated;

	/**
	 * Get Comment Titles From commentItems Separated With ' | ' Characters
	 * @return String Of Item's Comment Titles
	 */
	public String getCommentsString() {
		return commentItems.stream()
			.map(c -> c.getIdComment().getTitle())
			.collect(Collectors.joining(" | "));
	}

	/**
	 * Get Artist Titles From artistActivityItems Separated With ' | ' Characters. 'Actor' Activity Is Excluded
	 * @return String Of Item's Artist Titles
	 */
	public String getArtistsString() {
		return artistActivityItems.stream()
			.filter(a -> !"Actor".equals(a.getIdActivity().getTitle()))
			.map(a -> a.getIdArtist().getTitle())
			.collect(Collectors.joining(" | "));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("titleEng", titleEng)
			.append("titleEll", titleEll)
			.append("category", category)
			.append("photoPath", photoPath)
			.append("description", description)
			.append("year", year)
			.append("rating", rating)
			.append("subtitles", subtitles)
			.append("discs", discs)
			.append("place", place)
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
		Item rhs = (Item) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(titleEng, rhs.titleEng)
			.append(titleEll, rhs.titleEll)
			.append(category, rhs.category)
			.append(photoPath, rhs.photoPath)
			.append(description, rhs.description)
			.append(year, rhs.year)
			.append(rating, rhs.rating)
			.append(subtitles, rhs.subtitles)
			.append(discs, rhs.discs)
			.append(place, rhs.place)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(47, 53)
			.append(id)
			.append(titleEng)
			.append(titleEll)
			.append(category)
			.append(photoPath)
			.append(description)
			.append(year)
			.append(rating)
			.append(subtitles)
			.append(discs)
			.append(place)
			.toHashCode();
	}
}
