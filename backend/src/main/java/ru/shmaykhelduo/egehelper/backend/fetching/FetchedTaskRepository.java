package ru.shmaykhelduo.egehelper.backend.fetching;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FetchedTaskRepository extends JpaRepository<FetchedTask, UUID> {
    Optional<FetchedTask> findBySourceNameAndSourceTaskId(String sourceName, String sourceTaskId);
}
