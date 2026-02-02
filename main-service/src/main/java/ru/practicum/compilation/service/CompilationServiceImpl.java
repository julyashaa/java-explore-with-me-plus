package ru.practicum.compilation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final EventMapper eventMapper;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        String title = newCompilationDto.getTitle();
        log.info("Создание новой подборки: {}", title);

        throwIfTitleExist(title);

        Compilation compilation = compilationMapper.toEntity(newCompilationDto);

        Set<Long> eventsId = newCompilationDto.getEvents();
        if (eventsId != null && !eventsId.isEmpty()) {
            List<Event> events = eventRepository.findAllById(eventsId);
            compilation.setEvents(new HashSet<>(events));
        }

        Compilation savedCompilation = compilationRepository.save(compilation);
        log.info("Подборка создана с ID: {}", savedCompilation.getId());

        CompilationDto compilationDto = compilationMapper.toDto(savedCompilation);
        compilationDto.setEvents(mapEventsToEventsShortDto(savedCompilation.getEvents()));

        return compilationDto;
    }

    private void throwIfTitleExist(String title) {
        if (compilationRepository.existsByTitle(title)) {
            throw new ConflictException("Подборка с названием '" + title + "' уже существует");
        }
    }

    private Set<EventShortDto> mapEventsToEventsShortDto(Set<Event> events) {
        if (events == null || events.isEmpty()) {
            return Set.of();
        }
        return events.stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toSet());
    }
}
