package com.myblog.facade;

import com.myblog.domain.dto.CreatePostRequest;
import com.myblog.domain.dto.PostResponse;
import com.myblog.domain.dto.UpdatePostRequest;
import com.myblog.domain.entity.Post;
import com.myblog.mapper.PostMapper;
import com.myblog.service.PostService;
import com.myblog.service.impl.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostFacade {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostMapper postMapper;
    private final PostService postService;

    public PostFacade(PostMapper postMapper,
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
