package com.myblog.mapper;

import com.myblog.domain.dto.CreatePostRequest;
import com.myblog.domain.dto.PostResponse;
import com.myblog.domain.dto.UpdatePostRequest;
import com.myblog.domain.entity.Post;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PostMapper {

    public Post toEntity(CreatePostRequest request) {
        Instant now = Instant.now();

        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setImageUrl(request.imageUrl());
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        return post;
    }

    public Post toEntity(UpdatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setImageUrl(request.imageUrl());
        post.setUpdatedAt(Instant.now());

        return post;
    }

    public PostResponse toResponse(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getImageUrl(),
            post.getLikesCount(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}
