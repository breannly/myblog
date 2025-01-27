package com.myblog.services;

import com.myblog.entities.Post;

import java.util.List;

public interface PostService {

    Post create(Post post);

    Post update(Long id, Post post);

    void incrementLikes(Long id);

    Post findById(Long id);

    List<Post> findAll();

    void deleteById(Long id);
}
