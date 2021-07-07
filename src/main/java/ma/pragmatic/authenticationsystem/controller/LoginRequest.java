package ma.pragmatic.authenticationsystem.controller;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
