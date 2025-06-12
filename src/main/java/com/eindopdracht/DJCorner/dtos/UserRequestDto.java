package com.eindopdracht.DJCorner.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDto {

    @NotBlank
    @Size(min = 3, max = 15)
    public String userName;

    @Email
    public String email;

    @NotBlank
    @Size(min = 6, max = 25)
    public String password;
}
