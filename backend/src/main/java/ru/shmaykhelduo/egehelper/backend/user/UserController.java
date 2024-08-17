package ru.shmaykhelduo.egehelper.backend.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping("info")
    public UserResponse getUserInfo(@AuthenticationPrincipal User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }
}
