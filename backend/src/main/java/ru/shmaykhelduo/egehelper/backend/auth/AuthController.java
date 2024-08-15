package ru.shmaykhelduo.egehelper.backend.auth;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
@SecurityRequirements
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("signin")
    public TokenResponse signIn(SignInRequest request) {
        return authService.signIn(request);
    }

    @PostMapping("signup")
    public TokenResponse signUp(SignUpRequest request) {
        return authService.signUp(request);
    }
}
