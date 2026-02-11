package ru.practicum.comment.service;

import ru.practicum.comment.dto.*;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long userId, NewCommentDto newCommentDto);

    ShortCommentDto getComment(Long commentId);

    CommentDto getComment(Long userId, Long commentId);

    List<CommentDto> getUserComments(Long userId, GetCommentsDtoParams params);

    List<ShortCommentDto> getEventComments(Long eventId, GetCommentsDtoParams params);

    CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto newCommentDto);

    void deleteComment(Long userId, Long commentId);
}