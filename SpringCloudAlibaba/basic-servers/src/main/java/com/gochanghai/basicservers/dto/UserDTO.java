package com.gochanghai.basicservers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    @NotBlank(message = "{user.name.notBlank}")
    private String name;

    @NotBlank(message = "{user.idCard.notBlank}")
    private String idCard;

    @NotBlank(message = "{user.gender.notBlank}")
    private String gender;
}
