package es.upm.miw.devops.infrastructure.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String mobile;

    private String name;

    private String familyName;

    @Builder.Default
    @Column(name = "active")
    private Boolean isActive = false;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    public String fullName() {
        return this.name + " " + this.familyName;
    }

    public String initials() {
        String nameInitial = (this.name != null && !this.name.isEmpty()) ? this.name.substring(0, 1) + "." : "";
        String familyInitial = (this.familyName != null && !this.familyName.isEmpty()) ? this.familyName.substring(0, 1) + "." : "";
        return nameInitial + familyInitial;
    }
}