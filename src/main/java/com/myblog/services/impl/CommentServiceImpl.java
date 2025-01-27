package com.myblog.services.impl;

import com.myblog.entities.Comment;
import com.myblog.exceptions.CommentServiceException;
import com.myblog.exceptions.EntityNotFoundException;
import com.myblog.repositories.CommentRepository;
import com.myblog.repositories.PostRepository;
import com.myblog.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository,
                              CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(Comment comment) {
        logger.debug("Attempting to add comment for post with id: {}", comment.getPostId());
        validatePostExists(comment.getPostId());

        try {
            Comment savedComment = commentRepository.save(comment);
            logger.info("Successfully added comment with id: {} for post id: {}",
                savedComment.getId(), savedComment.getPostId());
            return savedComment;
        } catch (Exception e) {
            logger.error("Failed to add comment for post id: {}", comment.getPostId(), e);
            throw new CommentServiceException("Failed to add comment", e);
        }
    }

    @Override
    public Comment editComment(Comment comment) {
        logger.debug("Attempting to edit comment with id: {} for post id: {}",
            comment.getId(), comment.getPostId());
        validatePostExists(comment.getPostId());
        Comment existingComment = getCommentById(comment.getId());

        try {
            existingComment.setContent(comment.getContent());
            existingComment.setUpdatedAt(Instant.now());
            Comment savedComment = commentRepository.save(existingComment);
            logger.info("Successfully updated comment with id: {} for post id: {}",
                savedComment.getId(), savedComment.getPostId());
            return savedComment;
        } catch (Exception e) {
            logger.error("Failed to edit comment with id: {} for post id: {}",
                comment.getId(), comment.getPostId(), e);
            throw new CommentServiceException("Failed to edit comment", e);
        }
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        logger.debug("Attempting to delete comment with id: {} from post id: {}", commentId, postId);
        validatePostExists(postId);
        getCommentById(commentId);

        try {
            commentRepository.deleteById(commentId);
            logger.info("Successfully deleted comment with id: {} from post id: {}", commentId, postId);
        } catch (Exception e) {
            logger.error("Failed to delete comment with id: {} from post id: {}", commentId, postId, e);
            throw new CommentServiceException("Failed to delete comment", e);
        }
    }

    private void validatePostExists(Long postId) {
        if (!postRepository.existsById(postId)) {
            logger.warn("Post not found with id: {}", postId);
            throw new EntityNotFoundException("Post not found with id: " + postId);
        }
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
    }
}
