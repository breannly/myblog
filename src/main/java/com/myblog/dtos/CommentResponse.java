package com.myblog.dtos;

import java.time.Instant;

public record CommentResponse(
    Long id,
    Long postId,
    String content,
    Instant createdAt,
    Instant updatedAt
) {}
