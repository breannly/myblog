package com.myblog.mappers;

import com.myblog.dtos.CommentRequest;
import com.myblog.dtos.CommentResponse;
import com.myblog.entities.Comment;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CommentMapper {

    public Comment toEntity(Long id,
                            Long postId,
                            CommentRequest request) {
        Instant now = Instant.now();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setPostId(postId);
        comment.setContent(request.content());
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        return comment;
    }

    public Comment toEntity(Long postId,
                            CommentRequest request) {
        Instant now = Instant.now();

        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setContent(request.content());
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        return comment;
    }

    public CommentResponse toResponse(Comment comment) {
        return new CommentResponse(
            comment.getId(),
            comment.getPostId(),
            comment.getContent(),
            comment.getCreatedAt(),
            comment.getUpdatedAt()
        );
    }
}
