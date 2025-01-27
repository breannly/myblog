package com.myblog.facades.impl;

import com.myblog.dtos.CommentRequest;
import com.myblog.dtos.CommentResponse;
import com.myblog.entities.Comment;
import com.myblog.facades.CommentFacade;
import com.myblog.mappers.CommentMapper;
import com.myblog.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommentFacadeImpl implements CommentFacade {

    private static final Logger logger = LoggerFactory.getLogger(CommentFacadeImpl.class);

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    public CommentFacadeImpl(CommentMapper commentMapper,
                             CommentService commentService) {
        this.commentMapper = commentMapper;
        this.commentService = commentService;
    }

    @Override
    public CommentResponse addComment(Long postId, CommentRequest request) {
        logger.debug("Adding comment for post: {} from request: {}", postId, request);
        Comment comment = commentMapper.toEntity(postId, request);
        Comment addedComment = commentService.addComment(comment);
        return commentMapper.toResponse(addedComment);
    }

    @Override
    public CommentResponse editComment(Long postId, Long id, CommentRequest request) {
        logger.debug("Editing comment with id: {} for post: {} with request: {}", id, postId, request);
        Comment comment = commentMapper.toEntity(id, postId, request);
        Comment updatedComment = commentService.editComment(comment);
        return commentMapper.toResponse(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long id) {
        logger.debug("Deleting comment with id: {} from post: {}", id, postId);
        commentService.deleteComment(postId, id);
    }
}