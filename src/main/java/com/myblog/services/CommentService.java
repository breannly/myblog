package com.myblog.services;

import com.myblog.entities.Comment;

public interface CommentService {

    Comment addComment(Comment comment);

    Comment editComment(Comment comment);

    void deleteComment(Long postId, Long commentId);
}
