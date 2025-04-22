package filemanagement.dev.git.file.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer userid;

    private String username;

    private String email;

    private String passwordHash;

    private Integer uploadLimit;


}
