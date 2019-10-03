package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
 * Publisher Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Publishers")
@JsonPropertyOrder({ "id", "title", "dateUpdated" })
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "Id")
    private Integer id;

    @NotNull
    @Length(min = 3, max = 100)
    @Column(name = "Title")
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DateUpdated")
    @JsonIgnore
    private Calendar dateUpdated;

    /**
     * Publisher builder
     * @param title The title
     */
    @Builder
    public Publisher(String title) {
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
        Publisher rhs = (Publisher) obj;
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
        return new HashCodeBuilder(3, 29)
            .append(id)
            .append(title)
            .toHashCode();
    }
}
