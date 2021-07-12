package ma.pragmatic.authenticationsystem.model;

import lombok.Data;

@Data
public class PasswordRequest {
    private String password;
    private String matchingPassword;
    private String token;

}
