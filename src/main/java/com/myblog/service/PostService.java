package com.myblog.service;

import com.myblog.domain.entity.Post;

import java.util.List;

public interface PostService {

    Post create(Post post);

    Post update(Long id, Post post);

    Post findById(Long id);

    List<Post> findAll();

    void deleteById(Long id);

    void deleteAll();
}
