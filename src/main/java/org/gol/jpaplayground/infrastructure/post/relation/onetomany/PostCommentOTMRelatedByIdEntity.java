package org.gol.jpaplayground.infrastructure.post.relation.onetomany;

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
@NoArgsConstructor
@Table(name = "otm_post_comment")
class PostCommentOTMRelatedByIdEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "post_id")
    private UUID postId;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "author", length = 30)
    private String author;

    @Column(name = "content", length = 10000)
    private String content;

    PostCommentOTMRelatedByIdEntity(@NonNull UUID postId, @NonNull String author, @NonNull String content) {
        this.postId = postId;
        var now = LocalDateTime.now();
        this.creationDate = now.toLocalDate();
        this.creationTime = now;
        this.author = author;
        this.content = content;
    }
}
