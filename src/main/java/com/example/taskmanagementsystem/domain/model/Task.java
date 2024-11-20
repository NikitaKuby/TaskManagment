package com.example.taskmanagementsystem.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "emailAuthor", nullable = false)
    private String emailAuthorOfTheTask;

    @Column(name = "taskPerformer")
    private String taskPerformer;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusTask statusTask;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityTask priorityTask;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Set<TaskComments> comments = new HashSet<TaskComments>();

}
