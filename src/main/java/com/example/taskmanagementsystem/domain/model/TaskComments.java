package com.example.taskmanagementsystem.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TaskComments {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idComment;
    @Column(name = "emailCommentators", nullable = false)
    private String emailCommentators;

    @Column(nullable = false)
    private String comment;

    @Column(name="post_id", nullable = false)
    private Long postId;
    //@ManyToOne(fetch = FetchType.LAZY, optional = false)
    //@JoinColumn(name = "post_id", nullable = false)
    //private Task task;
}
