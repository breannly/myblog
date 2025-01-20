package com.myblog.domain.dto;

public record UpdatePostRequest(
    String title,
    String content,
    String imageUrl
) { }
