package org.RestAssured_Tutorials;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class DummyUser {

    private String firstName;
    private String   lastName;
    private String    age;
    private String     gender;
    private String     email;
    private String     phone;
    private String    username;
    private String    password;
}
