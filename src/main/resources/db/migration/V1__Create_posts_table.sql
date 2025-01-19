CREATE TABLE IF NOT EXISTS posts
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    image_url   VARCHAR(255) DEFAULT NULL,
    likes_count INTEGER      DEFAULT 0,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);