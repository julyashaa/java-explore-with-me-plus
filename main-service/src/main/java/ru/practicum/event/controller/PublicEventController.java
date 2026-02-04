package ru.practicum.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final EventService eventService;
    private final StatClient statClient;
    private EndpointHitDto endpointHitDto = EndpointHitDto.builder()
            .app("main-service").build();

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id, HttpServletRequest request) {
        EventFullDto eventFullDto = eventService.getPublishedEventById(id);
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setUri("/events/" + id);
        endpointHitDto.setTimestamp(LocalDateTime.now());
        statClient.hit(endpointHitDto);
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
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setUri("/events");
        endpointHitDto.setTimestamp(LocalDateTime.now());
        statClient.hit(endpointHitDto);
        return eventFullDtos;
    }
}