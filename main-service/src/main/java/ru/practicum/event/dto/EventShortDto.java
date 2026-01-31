package ru.practicum.event.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventShortDto {

    private Long id; // Идентификатор

    @NotBlank
    private String title; // Заголовок

    @NotBlank
    private String annotation; // Краткое описание

    @NotNull
    @Valid
    private CategoryDto category; // Категория

    private Long confirmedRequests; // Количество одобренных заявок

    @NotBlank
    private String eventDate; // Дата события в формате "yyyy-MM-dd HH:mm:ss"

    @NotNull
    @Valid
    private UserShortDto initiator; // Пользователь

    private Boolean paid; // Нужно ли платить

    private Long views; // Количество просмотров
}
