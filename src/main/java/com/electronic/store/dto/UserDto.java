package com.electronic.store.dto;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {


    private String userId;
    @NotNull
    @Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
    private String name;

    @NotEmpty
    @Pattern( regexp =  "^[a-zA-Z0-9]{6,12}$",message ="password must be given format only!!!")
    @Size(min=3, max= 50, message= "Password must be min 6 char and max 15 char!!!")
    private String password;

    @Email(message="email-Id must be in valid format!!!")
    private String email;

    @NotEmpty
    private String gender;

    private String about;

    private String image;
}
