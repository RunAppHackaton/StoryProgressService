package com.runapp.storyprogressservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Team_Task")
public class TeamTaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "task_id")
    private int taskId;

    @Column(name = "done")
    private int done;

    @Column(name = "team_id")
    private int team_id;
}
