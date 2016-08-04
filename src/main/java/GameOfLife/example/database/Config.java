package GameOfLife.example.database;

import javax.persistence.*;

/**
 * Created by raedschk on 04.08.2016.
 */

@Entity
@Table(name="CONFIG")
public class Config {
    @Id
    @Column(name="ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;
}
