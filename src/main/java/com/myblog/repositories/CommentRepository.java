package com.myblog.repositories;

import com.myblog.entities.Comment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT * FROM comments WHERE post_id = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);

    @Query("SELECT * FROM comments WHERE post_id IN (:postIds)")
    List<Comment> findAllByPostIdIn(@Param("postIds") Collection<Long> postIds);
}
