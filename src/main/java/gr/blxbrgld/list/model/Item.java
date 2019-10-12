package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gr.blxbrgld.list.jackson.ItemDeserializer;
import gr.blxbrgld.list.validators.*;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Item Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Items")
@NamedQueries({
	@NamedQuery(name = "findItemByTitleEng", query = "FROM Item WHERE titleEng = :titleEng"),
	@NamedQuery(name = "findItemsByCategory", query = "FROM Item WHERE category = :category OR category.parent = :category"),
	@NamedQuery(name = "findItemsByArtist", query = "SELECT DISTINCT i FROM Item i, IN (i.artistActivityItems) c WHERE c.idArtist = :artist ORDER BY i.titleEng"),
	@NamedQuery(name = "findItemsBySubtitles", query = "FROM Item WHERE subtitles = :subtitles"),
	@NamedQuery(name = "findItemsByPublisher", query = "FROM Item WHERE publisher = :publisher"),
	@NamedQuery(name = "countItemsHavingCategory", query = "SELECT COUNT(*) FROM Item WHERE category.title = :title")
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
@JsonDeserialize(using = ItemDeserializer.class)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@ApiModelProperty(readOnly = true, position = 0)
	private Integer id;

	//@Fields({ @Field, @Field(name = "sortTitle", analyze = Analyze.NO) }) //TODO Hibernate Search
	@NotNull
	@Length(min = 1, max = 255)
	@Column(name = "TitleEng")
	@ApiModelProperty(required = true, allowableValues = "range[1, 255]", position = 1)
	private String titleEng;
	
	//@Field //TODO Hibernate Search
	@Length(max = 255)
	@Column(name = "TitleEll")
	@ApiModelProperty(allowableValues = "range[1, 255]", position = 2)
	private String titleEll;
	
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "Category", referencedColumnName = "Id", nullable = false)
	@ApiModelProperty(required = true, dataType = "java.lang.String", allowableValues = "Popular Music,Classical Music,Greek Music,DVD Films,DivX Films,Books", position = 3)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "Publisher", referencedColumnName = "Id")
	@ApiModelProperty(dataType = "java.lang.String", position = 4)
	private Publisher publisher;
	
	@Length(max = 45)
	@Column(name = "photoPath")
	@ApiModelProperty(hidden = true)
	private String photoPath;

	@Transient
	@FileValid(message = "{gr.blxbrgld.list.validators.FileValid.message}")
	@JsonIgnore
	@ApiModelProperty(hidden = true) //TODO Expose photo
	private CommonsMultipartFile photo;
	
	//@Field //TODO Hibernate Search
	@Lob
	@Column(name = "Description", columnDefinition = "text")
	@ApiModelProperty(position = 5)
	private String description;

	@NullOrRange(min = 1900, max = 2100)
	@Column(name = "Year")
	@ApiModelProperty(position = 6)
	private Integer year;
	
	//@Field //TODO Hibernate Search
	//@NumericField //TODO Hibernate Search
	@Column(name = "Rating")
	@ApiModelProperty(position = 7)
	private Integer rating;
	
	@ManyToOne
	@JoinColumn(name = "Subtitles")
	@ApiModelProperty(dataType = "java.lang.String", allowableValues = "Greek Subtitles,English Subtitles,No Subtitles", position = 8)
	private Subtitles subtitles;

	@NullOrRange(min = 1, max = 100)
	@Column(name = "Discs")
	@ApiModelProperty(position = 9)
	private Integer discs;

	@NullOrMin(1)
	@Column(name = "Place")
	@ApiModelProperty(position = 10)
	private Integer place;

	@NullOrMin(10)
	@Column(name = "Pages")
	@ApiModelProperty(position = 11)
	private Integer pages;
	
	//@Field(name = "sortArtist", analyze = Analyze.NO) //TODO Hibernate Search
	//@FieldBridge(impl = ArtistActivityItemBridge.class) //TODO Hibernate Search
	//@IndexedEmbedded //TODO Hibernate Search
	@OneToMany(mappedBy = "idItem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy(clause = "id ASC")
	@Fetch(FetchMode.SELECT)
	@JsonProperty("artists")
	@JsonManagedReference
	@ApiModelProperty(position = 12)
	private List<ArtistActivityItem> artistActivityItems;
	
	@OneToMany(mappedBy = "idItem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@OrderBy(clause = "id ASC")
	@Fetch(FetchMode.SELECT)
	@JsonProperty("comments")
	@JsonManagedReference
	@ApiModelProperty(position = 13)
	private Set<CommentItem> commentItems;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Calendar dateUpdated;

	/**
	 * Item builder
	 * @param titleEng The english title
	 * @param titleEll The greek title
	 * @param category The category
	 * @param publisher The publisher
	 * @param description The description
	 * @param year The year
	 * @param rating The rating
	 * @param subtitles The subtitles
	 * @param discs The discs
	 * @param place The place
	 * @param pages The pages
	 * @param artists The artists as a map of artist name and activity
	 * @param comments The comments
	 */
	@Builder
	public Item(String titleEng, String titleEll, String category, String publisher, String description, Integer year, Integer rating, String subtitles, Integer discs, Integer place, Integer pages, Map<String, String> artists, List<String> comments) {
		this.titleEng = titleEng;
		this.titleEll = titleEll;
		this.category = Category.builder().title(category).build();
		if(StringUtils.trimToNull(publisher)!=null) {
			this.publisher = Publisher.builder().title(publisher).build();
		}
		this.description = description;
		this.year = year;
		this.rating = rating;
		if(StringUtils.trimToNull(subtitles)!=null) {
			this.subtitles = Subtitles.builder().title(subtitles).build();
		}
		this.discs = discs;
		this.place = place;
		this.pages = pages;
		this.commentItems = comments
			.stream()
			.map(c -> CommentItem
				.builder()
				.comment(c)
				.build()
			)
			.collect(Collectors.toSet());
		this.artistActivityItems = artists.entrySet()
			.stream()
			.map(e -> ArtistActivityItem
				.builder()
				.artist(e.getKey())
				.activity(e.getValue())
				.build()
			)
			.collect(Collectors.toList());
	}

	/**
	 * Get Comment Titles From commentItems Separated With ' | ' Characters
	 * @return String Of Item's Comment Titles
	 */
	@JsonIgnore
	public String getCommentsString() {
		return commentItems.stream()
			.map(c -> c.getIdComment().getTitle())
			.collect(Collectors.joining(" | "));
	}

	/**
	 * Get Artist Titles From artistActivityItems Separated With ' | ' Characters. 'Actor' Activity Is Excluded
	 * @return String Of Item's Artist Titles
	 */
	@JsonIgnore
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
