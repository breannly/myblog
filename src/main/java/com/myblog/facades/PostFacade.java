package com.myblog.facades;

import com.myblog.dtos.CreatePostRequest;
import com.myblog.dtos.PostResponse;
import com.myblog.dtos.UpdatePostRequest;

import java.util.List;

public interface PostFacade {

    PostResponse create(CreatePostRequest request);

    PostResponse update(Long id, UpdatePostRequest request);

    void incrementLikes(Long id);

    PostResponse findById(Long id);

    List<PostResponse> findAll();

    void deleteById(Long id);

    void deleteAll();
}
