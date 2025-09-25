package com.betacom.crudeliaDeAnimal.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignInDTO {
private Integer id;
private Boolean logged;
private String role;
}
