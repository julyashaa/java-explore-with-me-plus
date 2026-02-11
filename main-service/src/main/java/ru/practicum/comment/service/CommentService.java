package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;

public interface CommentService {

    CommentDto create(Long userId, NewCommentDto newCommentDto);
}
