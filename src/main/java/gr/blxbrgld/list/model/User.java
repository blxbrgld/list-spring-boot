package gr.blxbrgld.list.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gr.blxbrgld.list.jackson.UserDeserializer;
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
@NoArgsConstructor
@Entity
@Table(name = "Users")
@NamedQueries({
	@NamedQuery(name = "findUserByUsername", query = "FROM User WHERE username = :username"),
	@NamedQuery(name = "findUserByEmail", query = "FROM User WHERE email = :email"),
	@NamedQuery(name = "findUsersByRole", query = "FROM User WHERE role = :role")
})
@JsonPropertyOrder({ "id", "username", "password", "email", "role", "dateUpdated" })
@JsonDeserialize(using = UserDeserializer.class)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "Id")
	@ApiModelProperty(readOnly = true, position = 0)
	private Integer id;

	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Username")
	@ApiModelProperty(required = true, allowableValues = "range[3, 45]", position = 1)
	private String username;

	@NotNull
	@Length(min = 3, max = 60)
	@Column(name = "Password")
	@ApiModelProperty(required = true, allowableValues = "range[3, 60]", position = 2)
	private String password; //TODO It should be hidden when read

	@Email
	@NotNull
	@Length(min = 3, max = 45)
	@Column(name = "Email")
	@ApiModelProperty(required = true, allowableValues = "range[3, 45]", example = "johnDoe@gmail.com", position = 3)
	private String email;
	
	@ManyToOne
	@JoinColumn(name = "Role", referencedColumnName = "Id", nullable = false)
	@ApiModelProperty(required = true, dataType = "java.lang.String", example = "Administrator", position = 4)
	private Role role;

	@Column(name = "Enabled", columnDefinition = "TINYINT(1)")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private boolean enabled = true;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DateUpdated")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(dataType = "java.lang.String", readOnly = true, position = 5)
	private Calendar dateUpdated;

	/**
	 * User builder
	 * @param username The username
	 * @param password The password
	 * @param email The email
	 * @param role The role
	 */
	@Builder
	public User(String username, String password, String email, String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = Role.builder().title(role).build();
	}

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