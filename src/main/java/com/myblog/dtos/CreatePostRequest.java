package com.myblog.dtos;

public record CreatePostRequest(
   String title,
   String content,
   String imageUrl
) {}
