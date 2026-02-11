package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.GetCommentsDtoParams;
import ru.practicum.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto create(Long userId, NewCommentDto newCommentDto);

    CommentDto getComment(Long userId, Long commentId);

    List<CommentDto> getUserComments(Long userId, GetCommentsDtoParams params);
}