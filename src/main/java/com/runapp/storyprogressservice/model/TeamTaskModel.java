package com.runapp.storyprogressservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document("Team_Task")
public class TeamTaskModel {

    @Id
    @Field(name = "id")
    private String id;

    @Field(name = "user_id")
    private int userId;

    @Indexed
    @Field(name = "task_id")
    private int taskId;

    @Indexed
    @Field(name = "done")
    private Boolean done;

    @Indexed
    @Field(name = "team_id")
    private int teamId;
}
