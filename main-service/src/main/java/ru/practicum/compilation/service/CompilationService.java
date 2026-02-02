package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.GetCompilationsDtoParams;
import ru.practicum.compilation.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto compilationDto);

    CompilationDto getCompilationById(Long compId);

    List<CompilationDto> getCompilations(GetCompilationsDtoParams params);

    void deleteCompilation(Long compId);
}