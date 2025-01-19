package com.myblog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("posts")
public class Post implements Persistable<Long> {

    @Id
    private Long id;
    private String title;
    private String content;
    @Column(value = "image_url")
    private String imageUrl;
    @Column("created_at")
    private Instant createdAt;
    @Column("updated_at")
    private Instant updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
