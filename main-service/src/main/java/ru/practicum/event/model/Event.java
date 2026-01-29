package ru.practicum.event.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120, min = 3)
    private String title;

    @NotBlank
    @Size(max = 2000, min = 20)
    private String annotation;

    @NotNull
    private Long category;

    private Boolean paid;

    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    @Valid
    private Long initiator; // Пользователь

    @NotBlank
    @Size(max = 7000, min = 20)
    private String description;

    @NotNull
    private LocalDateTime createdOn;

    @Min(0)
    private Integer participantLimit;

    private Boolean requestModeration;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "lat")),
            @AttributeOverride(name = "lon", column = @Column(name = "lon"))
    })
    private Location location;
}
