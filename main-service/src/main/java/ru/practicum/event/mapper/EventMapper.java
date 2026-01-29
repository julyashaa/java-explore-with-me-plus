package ru.practicum.event.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "id", ignore = true)
    Event toEntity(NewEventDto dto);

    @Mapping(source = "requestModeration", target = "requestModeration")
    @Mapping(source = "createdOn", target = "createdOn")
    @Mapping(target = "initiator", ignore = true)
    EventFullDto toFullDto(Event event);

    @Mapping(target = "initiator", ignore = true)
    EventShortDto toShortDto(Event event);

    CategoryDto map(Long categoryId);

    // Мапперы для Location внутри интерфейса
    default Location toLocation(ru.practicum.event.model.Location locationDto) {
        if (locationDto == null) {
            return null;
        }
        return new Location(locationDto.getLat(), locationDto.getLon());
    }
}
