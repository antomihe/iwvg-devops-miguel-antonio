package es.upm.miw.devops.services.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFindCriteria {

    private String mobile;
    private String firstName;
    private String familyName;
    private Boolean isBillable;

    public boolean isAllNull() {
        return this.mobile == null && this.firstName == null && this.familyName == null && this.isBillable == null;
    }

    public boolean hasMobile() {
        return this.mobile != null && !this.mobile.isBlank();
    }

    public boolean hasFirstName() {
        return this.firstName != null && !this.firstName.isBlank();
    }

    public boolean hasFamilyName() {
        return this.familyName != null && !this.familyName.isBlank();
    }

    public boolean hasIsBillable() {
        return this.isBillable != null;
    }
}