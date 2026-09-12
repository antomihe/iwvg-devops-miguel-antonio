package es.upm.miw.devops.rest.dto;

import es.upm.miw.devops.data.model.User;

public class UserDto {

    private Long id;
    private String name;
    private String email;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}