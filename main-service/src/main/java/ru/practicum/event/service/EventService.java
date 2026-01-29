package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final UserService userService;

    @Transactional
    public EventFullDto create(NewEventDto dto, long userId) {
        Event event = eventMapper.toEntity(dto);
        UserDto userDto = userService.getUser(userId);
        UserShortDto userShortDto = new UserShortDto(userDto.getId(), userDto.getName());
        event.setCreatedOn(LocalDateTime.now());
        event.setInitiator(userId);
        Event eventSave = eventRepository.save(event);
        EventFullDto eventFullDto = eventMapper.toFullDto(eventSave);
        eventFullDto.setInitiator(userShortDto);
        return eventMapper.toFullDto(eventSave);
    }
}
