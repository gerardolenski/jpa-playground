package org.gol.jpaplayground.infrastructure.post.relation.onetomany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "otm_post_comment")
class PostCommentOTMEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    @Version
    @Column(name = "version")
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "pc_postId_fk"))
    private PostOTMEntity post;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Column(name = "author", length = 30)
    private String author;

    @Column(name = "content", length = 10000)
    private String content;

    PostCommentOTMEntity(@NonNull PostOTMEntity post, @NonNull String author, @NonNull String content) {
        this.post = post;
        var now = LocalDateTime.now();
        this.creationDate = now.toLocalDate();
        this.creationTime = now;
        this.author = author;
        this.content = content;
    }
}
