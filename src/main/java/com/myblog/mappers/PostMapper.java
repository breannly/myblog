package com.myblog.mappers;

import com.myblog.dtos.CreatePostRequest;
import com.myblog.dtos.PostResponse;
import com.myblog.dtos.UpdatePostRequest;
import com.myblog.entities.Post;
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
            post.getComments(),
            post.getCreatedAt(),
            post.getUpdatedAt()
        );
    }
}
