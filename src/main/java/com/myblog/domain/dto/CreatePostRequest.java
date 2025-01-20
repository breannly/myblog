package com.myblog.domain.dto;

public record CreatePostRequest(
   String title,
   String content,
   String imageUrl
) {}
