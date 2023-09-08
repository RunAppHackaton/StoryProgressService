package com.runapp.storyprogressservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionPercentageForUserInTeamResponse {

    private int user_id;

    private int team_id;

    private double percentage_complete;
}
