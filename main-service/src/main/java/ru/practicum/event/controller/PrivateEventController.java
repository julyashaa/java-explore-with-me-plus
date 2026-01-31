package ru.practicum.event.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.service.EventService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@Valid @RequestBody NewEventDto dto, @PathVariable long userId) {
        return eventService.create(dto, userId);
    }

    @GetMapping
    public List<EventShortDto> getUserEvents(
            @PathVariable Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return eventService.getEventsForUser(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventDto getEvent(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody UpdateEventUserRequest request) {

        return eventService.updateEvent(userId, eventId, request);
    }
}