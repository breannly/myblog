package com.myblog.facades.impl;

import com.myblog.dtos.CreatePostRequest;
import com.myblog.dtos.PostResponse;
import com.myblog.dtos.UpdatePostRequest;
import com.myblog.entities.Post;
import com.myblog.facades.PostFacade;
import com.myblog.mappers.PostMapper;
import com.myblog.services.PostService;
import com.myblog.services.impl.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFacadeImpl implements PostFacade {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostMapper postMapper;
    private final PostService postService;

    public PostFacadeImpl(PostMapper postMapper,
                          PostService postService) {
        this.postMapper = postMapper;
        this.postService = postService;
    }

    public PostResponse create(CreatePostRequest request) {
        logger.debug("Creating post from request: {}", request);
        Post post = postMapper.toEntity(request);
        Post createdPost = postService.create(post);
        return postMapper.toResponse(createdPost);
    }

    public PostResponse update(Long id, UpdatePostRequest request) {
        logger.debug("Updating post with id: {} and request: {}", id, request);
        Post post = postMapper.toEntity(request);
        Post updatedPost = postService.update(id, post);
        return postMapper.toResponse(updatedPost);
    }

    public void incrementLikes(Long id) {
        logger.debug("Incrementing likes for post with id: {}", id);
        postService.incrementLikes(id);
    }

    public PostResponse findById(Long id) {
        logger.debug("Finding post by id: {}", id);
        Post post = postService.findById(id);
        return postMapper.toResponse(post);
    }

    public List<PostResponse> findAll() {
        logger.debug("Finding all posts");
        List<Post> posts = postService.findAll();
        return posts.stream()
            .map(postMapper::toResponse)
            .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        logger.debug("Deleting post by id: {}", id);
        postService.deleteById(id);
    }

    public void deleteAll() {
        logger.debug("Deleting all posts");
        postService.deleteAll();
    }
}
