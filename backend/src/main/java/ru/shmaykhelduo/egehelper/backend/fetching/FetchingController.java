package ru.shmaykhelduo.egehelper.backend.fetching;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fetching/")
@RequiredArgsConstructor
public class FetchingController {
    private final FetchingService fetchingService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void startFetching() {
        fetchingService.fetchTasks();
    }
}
