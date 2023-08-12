package ogjg.instagram.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ogjg.instagram.user.dto.SignupRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return usersService.registerUser(signupRequestDto);
    }
}
