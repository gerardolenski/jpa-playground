package org.gol.jpaplayground.infrastructure.post.relation.manytoone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@Table(name = "mto_post")
@NoArgsConstructor
class PostMTOEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "author", length = 30)
    private String author;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "topic", length = 100)
    private String topic;

    @Column(name = "content", length = 10000)
    private String content;

    @Column(name = "comment_count")
    private long commentCount;

    PostMTOEntity(@NonNull String author, @NonNull String category, @NonNull String topic, @NonNull String content) {
        var now = LocalDateTime.now();
        this.creationDate = now.toLocalDate();
        this.creationTime = now;
        this.author = author;
        this.category = category;
        this.topic = topic;
        this.content = content;
    }

    PostMTOEntity incrementCommentCount() {
        commentCount++;
        return this;
    }
}
