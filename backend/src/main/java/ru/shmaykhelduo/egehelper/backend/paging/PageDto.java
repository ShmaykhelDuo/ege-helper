package ru.shmaykhelduo.egehelper.backend.paging;

import java.util.List;

public record PageDto<E>(List<E> elements, long totalElements, long totalPages, long number) {
}
