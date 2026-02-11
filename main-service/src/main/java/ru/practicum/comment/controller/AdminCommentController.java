package ru.practicum.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.GetCommentsAdminDtoParams;
import ru.practicum.comment.service.CommentService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@Valid @ModelAttribute GetCommentsAdminDtoParams params) {
        log.info("Запрос на получение комментариев админом: GET /admin/comments с параметрами {}", params);
        return commentService.getCommentsByParams(params);
    }
}