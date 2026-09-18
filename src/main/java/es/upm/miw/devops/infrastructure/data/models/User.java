package es.upm.miw.devops.infrastructure.data.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Boolean active;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return this.familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return this.name;
    }

    public void setFirstName(String firstName) {
        this.name = firstName;
    }

    public String fullName() {
        return this.name + " " + this.familyName;
    }

    public String initials() {
        String nameInitial = (this.name != null && !this.name.isEmpty()) ? this.name.substring(0, 1) + "." : "";
        String familyInitial = (this.familyName != null && !this.familyName.isEmpty()) ? this.familyName.substring(0, 1) + "." : "";
        return nameInitial + familyInitial;
    }
}
