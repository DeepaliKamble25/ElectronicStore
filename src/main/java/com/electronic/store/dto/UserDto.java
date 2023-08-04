package com.electronic.store.dto;

import com.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Embedded;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto  {


    private String userId;
    @NotBlank(message = "User name cannot be blank")
    @Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
    private String name;

    @NotEmpty
    @Pattern( regexp =  "^[a-zA-Z0-9]{6,12}$",message ="password must be given format only!!!")
    @Size(min=3, max= 50, message= "Password must be required in given format !!!")
    private String password;

  //  @Pattern(regexp="^[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])*$")
    @Email(message="email-Id must be in valid format!!!")
    @NotBlank(message = "Email cannot be blank!!!")
    private String email;

    @NotBlank(message = "insert gender details")
    @Size (min=4, max=6, message="Invalid gender!!!!")
    private String gender;

    @NotBlank
    @Size(min=6, max=100, message = "write something about yourSelf!!!")
    private String about;

    @ImageNameValid
    private String image;

    @Embedded
    private BaseDto baseDto;
}
