package ma.pragmatic.authenticationsystem.controller;

import ma.pragmatic.authenticationsystem.config.jwt.JwtProvider;
import ma.pragmatic.authenticationsystem.model.EmailRequest;
import ma.pragmatic.authenticationsystem.model.LoginRequest;
import ma.pragmatic.authenticationsystem.model.PasswordRequest;
import ma.pragmatic.authenticationsystem.model.User;
import ma.pragmatic.authenticationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws FileNotFoundException {
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User was successfully registered");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        User loggedInUser = userService.getByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        String generatedToken = jwtProvider.generateToken(loggedInUser.getEmail());
        return ResponseEntity.ok().body(generatedToken);
    }

    @GetMapping("/register/confirm")
    public String confirmRegister(@RequestParam("token") String token) {
        return userService.confirmRegisterToken(token);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> sendResetEmail(@RequestBody EmailRequest emailRequest) {
        userService.sendResetPasswordMail(emailRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body("Reset password email has sent");
    }

    @GetMapping("/reset-password")
    public ResponseEntity<String> confirmPassword(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.confirmPasswordToken(token));
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordRequest passwordRequest) {
        userService.updatePassword(passwordRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Your password has been updated!");
    }
}
