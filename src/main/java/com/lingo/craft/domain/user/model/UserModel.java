package com.lingo.craft.domain.user.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    private UUID id;
    private String name;
    private String password;
    private String email;
    private String phone;
    private int age;
    private String gender;
    private int roleId;

}
