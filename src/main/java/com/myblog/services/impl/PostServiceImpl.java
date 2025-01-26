package com.myblog.services.impl;

import com.myblog.entities.Post;
import com.myblog.exceptions.PostNotFoundException;
import com.myblog.exceptions.PostServiceException;
import com.myblog.repositories.PostRepository;
import com.myblog.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post create(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }

        logger.debug("Attempting to create post: {}", post);
        try {
            Post savedPost = postRepository.save(post);
            logger.info("Successfully created post with id: {}", savedPost.getId());
            return savedPost;
        } catch (Exception e) {
            logger.error("Failed to create post: {}", post, e);
            throw new PostServiceException("Failed to create post", e);
        }
    }

    @Override
    public Post update(Long id, Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }

        logger.debug("Attempting to update post with id: {}", id);
        Post foundPost = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        try {
            Post enrichedPost = enrichPost(post, foundPost);
            Post updatedPost = postRepository.save(enrichedPost);
            logger.info("Successfully updated post with id: {}", id);
            return updatedPost;
        } catch (Exception e) {
            logger.error("Failed to update post with id: {}", id, e);
            throw new PostServiceException("Failed to update post", e);
        }
    }

    @Override
    public void incrementLikes(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        logger.debug("Attempting to increment likes for post with id: {}", id);
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post not found with id: " + id));

        try {
            postRepository.save(post.incrementLikes());
            logger.info("Successfully increment likes for post with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to increment likes for post with id: {}", id, e);
            throw new PostServiceException("Failed to increment post's likes", e);
        }
    }

    @Override
    public Post findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        logger.debug("Searching for post with id: {}", id);
        return postRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Post not found with id: {}", id);
                return new PostNotFoundException("Post not found with id: " + id);
            });
    }

    @Override
    public List<Post> findAll() {
        logger.debug("Retrieving all posts");
        try {
            List<Post> posts = StreamSupport
                .stream(postRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
            logger.info("Successfully retrieved {} posts", posts.size());
            return posts;
        } catch (Exception e) {
            logger.error("Failed to retrieve posts", e);
            throw new PostServiceException("Failed to retrieve posts", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        logger.debug("Attempting to delete post with id: {}", id);
        validatePostExists(id);

        try {
            postRepository.deleteById(id);
            logger.info("Successfully deleted post with id: {}", id);
        } catch (Exception e) {
            logger.error("Failed to delete post with id: {}", id, e);
            throw new PostServiceException("Failed to delete post", e);
        }
    }

    @Override
    public void deleteAll() {
        logger.warn("Attempting to delete all posts");
        try {
            postRepository.deleteAll();
            logger.info("Successfully deleted all posts");
        } catch (Exception e) {
            logger.error("Failed to delete all posts", e);
            throw new PostServiceException("Failed to delete all posts", e);
        }
    }

    private Post enrichPost(Post target, Post source) {
        if (target.getTitle() != null) {
            source.setTitle(target.getTitle());
        }
        if (target.getContent() != null) {
            source.setContent(target.getContent());
        }
        if (target.getImageUrl() != null) {
            source.setImageUrl(target.getImageUrl());
        }
        source.setUpdatedAt(target.getUpdatedAt());
        return source;
    }

    private void validatePostExists(Long id) {
        if (!postRepository.existsById(id)) {
            logger.warn("Post not found with id: {}", id);
            throw new PostNotFoundException("Post not found with id: " + id);
        }
    }
}
