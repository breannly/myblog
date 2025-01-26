package com.myblog.dtos;

import java.time.Instant;

public record PostResponse(
   Long id,
   String title,
   String content,
   String imageUrl,
   Integer likesCount,
   Instant createdAt,
   Instant updatedAt
) {
}
