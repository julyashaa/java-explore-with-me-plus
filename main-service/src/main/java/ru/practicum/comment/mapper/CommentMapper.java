package ru.practicum.comment.mapper;

import org.mapstruct.Mapper;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    Comment toEntity(NewCommentDto newCommentDto);
}