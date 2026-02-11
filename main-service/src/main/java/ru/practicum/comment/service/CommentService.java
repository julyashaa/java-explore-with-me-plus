package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.GetCommentsDtoParams;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long userId, NewCommentDto newCommentDto);

    CommentDto getComment(Long userId, Long commentId);

    List<CommentDto> getUserComments(Long userId, GetCommentsDtoParams params);

    CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto newCommentDto);

    void deleteComment(Long userId, Long commentId);
}