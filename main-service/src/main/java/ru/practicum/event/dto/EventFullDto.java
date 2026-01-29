package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.Location;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.constants.DatePatternConstant.DATE_TIME_PATTERN;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventFullDto {

    private Long id; // Идентификатор

    @NotBlank
    private String title; // Заголовок

    @NotBlank
    private String annotation; // Краткое описание

    @NotNull
    @Valid
    private CategoryDto category; // Категория

    private Boolean paid; // Нужно ли оплачивать

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private UserShortDto initiator; // Пользователь

    @NotBlank
    private String description; // Полное описание

    @Min(0)
    private Integer participantLimit; // Ограничение участников (0 = нет ограничения)

    private String state; // Статус события

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private String createdOn; // Дата и время создания

    @NotNull
    @Valid
    private Location location; // Место проведения

    private Integer confirmedRequests; // Количество одобренных заявок

    private String publishedOn; // Дата публикации

    private Long views; // Количество просмотров

    private Boolean requestModeration;
}