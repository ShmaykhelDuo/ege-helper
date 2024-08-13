package ru.shmaykhelduo.egehelper.backend.fetching;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fetching/")
@RequiredArgsConstructor
public class FetchingController {
    private final FetchingService fetchingService;

    @PostMapping
    public void startFetching() {
        fetchingService.fetchTasks();
    }
}
