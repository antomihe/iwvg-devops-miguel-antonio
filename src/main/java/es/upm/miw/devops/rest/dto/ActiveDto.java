package es.upm.miw.devops.rest.dto;

public class ActiveDto {

    private Boolean active;

    public ActiveDto() {
    }

    public ActiveDto(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}