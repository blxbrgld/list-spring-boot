package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Calendar;

/**
 * User Domain Object
 * @author blxbrgld
 */
@Getter
@Setter
@Entity
@Table(name = "Users")
@NamedQueries({
	@NamedQuery(name = "findUserByUsername", query = "FROM User WHERE username = :username"),
	@NamedQuery(name = "findUserByEmail", query = "FROM User WHERE email = :email"),
	@NamedQuery(name = "findUsersByRole", query = "FROM User WHERE role = :role")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	private Integer id;

	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Username")
	private String username;

	@Email
	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "Role", referencedColumnName = "Id", nullable = false)
	@JsonManagedReference //TODO Review This
	private Role role;

	@NotNull
	@Column(name = "Enabled", columnDefinition = "TINYINT(1)")
	private boolean enabled = true;

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
			.append("username", username)
			.append("email", email)
			.append("role", role)
			.append("enabled", enabled)
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
		User rhs = (User) obj;
		return new EqualsBuilder()
			.append(id, rhs.id)
			.append(username, rhs.username)
			.append(email, rhs.email)
			.append(role, rhs.role)
			.append(enabled, rhs.enabled)
			.isEquals();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(67, 71)
			.append(id)
			.append(username)
			.append(email)
			.append(role)
			.append(enabled)
			.toHashCode();
	}
}