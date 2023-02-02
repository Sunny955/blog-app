package com.blogapp.blogappiapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class UserDto implements Serializable{
    private Integer id;
    @NotEmpty
    @Size(min=4,max=50,message = "Username must be of min 4 characters and max of 50 characters!")
    private String name;
    @Email(message = "Email address isn't valid!")
    private String email;
    @NotEmpty
    @Size(min=3,max=15,message = "Password must be min of 3 characters and max of 15 characters!")
    private String password;
    @NotEmpty
    private String about;
}