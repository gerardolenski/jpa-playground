package org.gol.jpaplayground.infrastructure.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import static java.util.UUID.randomUUID;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Table(name = "post_seq_id")
@NoArgsConstructor
class SequencedIdPostEntity {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column(name = "id")
    private long id;

    @Setter
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Setter
    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    @Setter
    @Column(name = "author", length = 30)
    private String author;

    @Setter
    @Column(name = "category", length = 50)
    private String category;

    @Setter
    @Column(name = "topic", length = 100)
    private String topic;

    @Setter
    @Column(name = "content", length = 10000)
    private String content;

    SequencedIdPostEntity(@NonNull String author, @NonNull String category, @NonNull String topic, @NonNull String content) {
        var now = LocalDateTime.now();
        this.creationDate = now.toLocalDate();
        this.creationTime = now;
        this.author = author;
        this.category = category;
        this.topic = topic;
        this.content = content;
    }
}
