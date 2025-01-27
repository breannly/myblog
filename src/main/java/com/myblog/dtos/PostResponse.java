package com.myblog.dtos;

import com.myblog.entities.Comment;

import java.time.Instant;
import java.util.List;

public record PostResponse(
   Long id,
   String title,
   String content,
   String imageUrl,
   Integer likesCount,
   List<Comment> comments,
   Instant createdAt,
   Instant updatedAt
) {}
