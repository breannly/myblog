package com.myblog.dtos;

public record UpdatePostRequest(
    String title,
    String content,
    String imageUrl
) { }
