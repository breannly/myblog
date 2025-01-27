package com.myblog.services.impl;

import com.myblog.entities.Comment;
import com.myblog.entities.Post;
import com.myblog.exceptions.EntityNotFoundException;
import com.myblog.exceptions.PostServiceException;
import com.myblog.repositories.CommentRepository;
import com.myblog.repositories.PostRepository;
import com.myblog.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository,
                           CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Post create(Post post) {
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
        logger.debug("Attempting to update post with id: {}", id);
        Post foundPost = postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

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
        logger.debug("Attempting to increment likes for post with id: {}", id);
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));

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
        logger.debug("Searching for post with id: {}", id);
        Post post = postRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Post not found with id: {}", id);
                return new EntityNotFoundException("Post not found with id: " + id);
            });

        List<Comment> comments = commentRepository.findAllByPostId(post.getId()).stream()
            .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
            .collect(Collectors.toList());
        post.setComments(comments);
        return post;
    }

    @Override
    public List<Post> findAll() {
        logger.debug("Retrieving all posts");
        try {
            List<Post> posts = StreamSupport
                .stream(postRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

            Set<Long> postIds = posts.stream()
                .map(Post::getId)
                .collect(Collectors.toSet());

            List<Comment> comments = commentRepository.findAllByPostIdIn(postIds);

            Map<Long, List<Comment>> commentsByPostId = comments.stream()
                .collect(Collectors.groupingBy(Comment::getPostId));
            commentsByPostId.forEach((postId, commentList) ->
                commentList.sort(Comparator.comparing(Comment::getCreatedAt).reversed()));

            posts.forEach(post ->
                post.setComments(commentsByPostId.getOrDefault(post.getId(), Collections.emptyList())));

            logger.info("Successfully retrieved {} posts with {} comments", posts.size(), comments.size());
            return posts;
        } catch (Exception e) {
            logger.error("Failed to retrieve posts", e);
            throw new PostServiceException("Failed to retrieve posts", e);
        }
    }

    @Override
    public void deleteById(Long id) {
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
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
    }
}
