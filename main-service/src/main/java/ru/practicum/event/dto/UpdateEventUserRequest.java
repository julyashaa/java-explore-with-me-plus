package ru.practicum.event.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import ru.practicum.event.enums.EventStateActionEnum;
import ru.practicum.event.model.Location;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private String annotation; // Новая аннотация

    private Long category; // Новая категория (ID)

    @Size(min = 20, max = 7000)
    private String description; // Новое описание

    private String eventDate; // Новые дата и время события в формате "yyyy-MM-dd HH:mm:ss"

    private Location location; // Место проведения

    private Boolean paid; // Новое значение флага платности

    @Min(0)
    private Integer participantLimit; // Новый лимит участников

    private Boolean requestModeration; // Нужна ли пре-модерация заявок

    private EventStateActionEnum stateAction; // Изменение статуса события

    @Size(min = 3, max = 120)
    private String title; // Новый заголовок
}