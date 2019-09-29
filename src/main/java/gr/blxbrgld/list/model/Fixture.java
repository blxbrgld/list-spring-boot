package gr.blxbrgld.list.model;

import gr.blxbrgld.list.enums.FixtureType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Fixtures are a way to ensure that data created by integration tests would be deleted. Normally the data would be deleted by the @After, @AfterClass hooks
 * but we need a way to be sure that we'll handle an erroneous case. So a scheduled task is introduced which deletes values found in fixtures from the related tables
 * @author blxbrgld
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Fixtures")
@NamedQuery(name = "getFixturesByType", query = "FROM Fixture WHERE type=:type")
public class Fixture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "Id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type")
    private FixtureType type;

    @Column(name = "Fixture")
    private String fixture;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DateUpdated")
    private Calendar dateUpdated;

    /**
     * Constructor
     * @param type {@link FixtureType}
     * @param fixture The fixture as a JSON string
     */
    public Fixture(FixtureType type, String fixture) {
        this.type = type;
        this.fixture = fixture;
    }
}
