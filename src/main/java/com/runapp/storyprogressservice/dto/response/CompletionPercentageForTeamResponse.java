package com.runapp.storyprogressservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionPercentageForTeamResponse {

    private int team_id;

    private double percentage_complete;
}
