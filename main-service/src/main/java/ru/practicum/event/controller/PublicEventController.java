package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.ClientForStat;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.service.EventService;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;
    private final ClientForStat client = new ClientForStat();

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        EventFullDto eventFullDto = eventService.getPublishedEventById(id);
        client.hit(request.getRemoteAddr(), "/events/" + id);
        return eventFullDto;
    }

    @GetMapping
    public List<EventFullDto> getEvents(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) String rangeStart,
            @RequestParam(required = false) String rangeEnd,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        List<EventFullDto> eventFullDtos = eventService.getEvents(users, states, categories, rangeStart, rangeEnd,
                from, size);
        client.hit(request.getRemoteAddr());
        return eventFullDtos;
    }
}