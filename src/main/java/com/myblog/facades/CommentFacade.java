package com.myblog.facades;

import com.myblog.dtos.CommentRequest;
import com.myblog.dtos.CommentResponse;
import com.myblog.entities.Comment;

public interface CommentFacade {

    CommentResponse addComment(Long postId, CommentRequest request);

    CommentResponse editComment(Long postId, Long id, CommentRequest request);

    void deleteComment(Long postId, Long id);
}
