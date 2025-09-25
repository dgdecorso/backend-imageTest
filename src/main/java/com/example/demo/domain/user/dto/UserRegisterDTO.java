package com.example.demo.domain.user.dto;

import com.example.demo.core.generic.AbstractDTO;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserRegisterDTO extends AbstractDTO {

  private String firstName;

  private String lastName;

  @Email
  private String email;

  private String password;

}
