package ru.practicum.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.service.CommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto createComment(@PathVariable Long userId,
                                    @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("Запрос на создание комментария: POST /users/{}/comments", userId);
        return commentService.create(userId, newCommentDto);
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long userId,
                                 @PathVariable Long commentId) {
        log.info("Запрос на получение комментария: Get /users/{}/comments/{}", userId, commentId);
        return commentService.getComment(userId, commentId);
    }
}