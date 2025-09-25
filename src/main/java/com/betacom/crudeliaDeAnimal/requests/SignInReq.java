package com.betacom.crudeliaDeAnimal.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignInReq {
    private String email;
    private String password;
}
