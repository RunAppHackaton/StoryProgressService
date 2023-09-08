package com.runapp.storyprogressservice.dto.request;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamTaskRequest {

    private int userId;

    private int taskId;

    private int done;

    private int team_id;

    public TeamTaskModel toTeamTaskModel() {
        TeamTaskModel teamTaskModel = new TeamTaskModel();
        teamTaskModel.setUserId(this.userId);
        teamTaskModel.setTaskId(this.taskId);
        teamTaskModel.setDone(this.done);
        teamTaskModel.setTeam_id(this.team_id);
        return teamTaskModel;
    }
}
