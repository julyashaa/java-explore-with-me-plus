package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.*;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ForbiddenException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommentDto createComment(Long userId, NewCommentDto newCommentDto) {
        log.info("Создание нового комментария: {}", newCommentDto);

        User user = getUserOrElseThrow(userId);
        Event event = getEventOrElseThrow(newCommentDto.getEventId());

        Comment comment = commentMapper.toEntity(newCommentDto);
        comment.setCreatedOn(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setEvent(event);

        Comment savedComment = commentRepository.save(comment);
        log.info("Комментарий создан с id: {}", savedComment.getId());

        CommentDto result = commentMapper.toDto(savedComment);
        result.setAuthor(userMapper.toShortDto(user));

        return result;
    }

    @Override
    public ShortCommentDto getComment(Long commentId) {
        log.info("Получение комментария с id: {}", commentId);

        Comment comment = getCommentOrElseThrow(commentId);

        return commentMapper.toShortDto(comment);
    }

    @Override
    public CommentDto getComment(Long userId, Long commentId) {
        log.info("Получение комментария с id: {}", commentId);

        User user = getUserOrElseThrow(userId);
        Comment comment = getCommentOrElseThrow(commentId);

        CommentDto result = commentMapper.toDto(comment);
        result.setAuthor(userMapper.toShortDto(user));

        return result;
    }

    @Override
    public List<CommentDto> getUserComments(Long userId, GetCommentsDtoParams params) {
        log.info("Получение комментариев пользователя с параметрами: {}", params);

        Pageable page = PageRequest.of(
                params.getFrom() / params.getSize(),
                params.getSize(),
                Sort.by("createdOn").descending());

        List<Comment> result = commentRepository.findByAuthorId(userId, page);

        return mapToListCommentDto(result);
    }

    @Override
    public List<ShortCommentDto> getEventComments(Long eventId, GetCommentsDtoParams params) {
        log.info("Получение комментариев ивента с параметрами: {}", params);

        Pageable page = PageRequest.of(
                params.getFrom() / params.getSize(),
                params.getSize(),
                Sort.by("createdOn").descending());

        List<Comment> result = commentRepository.findByEventId(eventId, page);

        return  mapToListShortCommentDto(result);
    }

    @Override
    public CommentDto updateComment(Long userId, Long commentId, UpdateCommentDto updateCommentDto) {
        log.info("Обновление комментария с id: {}", commentId);

        User user = getUserOrElseThrow(userId);
        Comment comment = getCommentOrElseThrow(commentId);

        throwIfUserNotAuthorComment(userId, comment);

        String text = updateCommentDto.getText();
        if (text != null) {
            comment.setText(text);
            comment.setEditedOn(LocalDateTime.now());
        }
        Comment savedComment = commentRepository.save(comment);
        log.info("Комментарий обновлен {}", savedComment);

        CommentDto result = commentMapper.toDto(savedComment);
        result.setAuthor(userMapper.toShortDto(user));
        return result;
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        log.info("Удаление комментария с id: {}", commentId);

        Comment comment = getCommentOrElseThrow(commentId);

        throwIfUserNotAuthorComment(userId, comment);

        commentRepository.delete(comment);

        log.info("Комментарий с id {} удален", userId);
    }

    private List<CommentDto> mapToListCommentDto(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        return comments.stream()
                .map(comment -> {
                            CommentDto dto = commentMapper.toDto(comment);
                            dto.setAuthor(userMapper.toShortDto(comment.getAuthor()));
                            return dto;
                        }
                )
                .toList();
    }

    private List<ShortCommentDto> mapToListShortCommentDto(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return Collections.emptyList();
        }

        return comments.stream()
                .map(commentMapper::toShortDto)
                .toList();
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
    }

    private Event getEventOrElseThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id " + eventId + " not found"));
    }

    private Comment getCommentOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id " + commentId + " not found"));
    }

    private void throwIfUserNotAuthorComment(Long userId, Comment comment) {
        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("User with id " + userId + " not author comment");
        }
    }
}