package com.runapp.storyprogressservice.dto.request;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamTaskRequest {

    private String userId;

    private int taskId;

    private boolean done;

    private int team_id;

    public TeamTaskModel toTeamTaskModel() {
        TeamTaskModel teamTaskModel = new TeamTaskModel();
        teamTaskModel.setUserId(this.userId);
        teamTaskModel.setTaskId(this.taskId);
        teamTaskModel.setDone(this.done);
        teamTaskModel.setTeamId(this.team_id);
        return teamTaskModel;
    }
}
