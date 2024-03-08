package com.runapp.storyprogressservice.controller;

import com.runapp.storyprogressservice.dto.request.TeamTaskRequest;
import com.runapp.storyprogressservice.dto.response.CompletionPercentageForTeamResponse;
import com.runapp.storyprogressservice.dto.response.CompletionPercentageForUserInTeamResponse;
import com.runapp.storyprogressservice.model.TeamTaskModel;
import com.runapp.storyprogressservice.service.TeamTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team-tasks")
@Tag(name = "Team Task Management", description = "Operations related to team tasks")
public class TeamTaskController {

    private final TeamTaskService teamTaskService;

    public TeamTaskController(TeamTaskService teamTaskService) {
        this.teamTaskService = teamTaskService;
    }

    @GetMapping
    @Operation(summary = "Get all team tasks", description = "Retrieve a list of all team tasks")
    public List<TeamTaskModel> getAllTeamTasks() {
        return teamTaskService.getAllTeamTasks();
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "Get a team task by ID", description = "Retrieve a team task by its ID")
    @ApiResponse(responseCode = "200", description = "Team task found", content = @Content(schema = @Schema(implementation = TeamTaskModel.class)))
    @ApiResponse(responseCode = "404", description = "Team task not found")
    public ResponseEntity<TeamTaskModel> getTeamTaskById(
            @Parameter(description = "Team task ID", required = true)
            @PathVariable String taskId) {
        Optional<TeamTaskModel> teamTask = teamTaskService.getTeamTaskById(taskId);
        return teamTask.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @Operation(summary = "Create a new team task", description = "Create a new team task with the provided data")
    @ApiResponse(responseCode = "201", description = "Team task created", content = @Content(schema = @Schema(implementation = TeamTaskRequest.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<TeamTaskModel> createTeamTask(
            @Parameter(description = "Team task data", required = true)
            @RequestBody TeamTaskRequest teamTaskRequest,
            @RequestHeader("X-UserId") String userId) {
        teamTaskRequest.setUserId(userId);
        TeamTaskModel createdTeamTask = teamTaskService.createTeamTask(teamTaskRequest.toTeamTaskModel());
        return new ResponseEntity<>(createdTeamTask, HttpStatus.CREATED);
    }

        @PutMapping("/{taskId}")
        @Operation(summary = "Update a team task", description = "Update an existing team task with the provided data")
        @ApiResponse(responseCode = "200", description = "Team task updated", content = @Content(schema = @Schema(implementation = TeamTaskRequest.class)))
        @ApiResponse(responseCode = "400", description = "Invalid input")
        @ApiResponse(responseCode = "404", description = "Team task not found")
        public ResponseEntity<TeamTaskModel> updateTeamTask(
            @Parameter(description = "Updated team task data", required = true)
            @PathVariable("taskId") int taskId,
            @RequestBody TeamTaskRequest teamTaskRequest,
            @RequestHeader("X-UserId") String userId) {
            TeamTaskModel updatedTask = teamTaskRequest.toTeamTaskModel();
            updatedTask.setId(taskId);
            updatedTask = teamTaskService.updateTeamTask(updatedTask);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }

    @DeleteMapping("/{taskId}")
    @Operation(summary = "Delete a team task", description = "Delete a team task by its ID")
    @ApiResponse(responseCode = "204", description = "Team task deleted")
    @ApiResponse(responseCode = "404", description = "Team task not found")
    public ResponseEntity<Void> deleteTeamTask(
            @Parameter(description = "Team task ID", required = true)
            @PathVariable String taskId) {
        teamTaskService.deleteTeamTask(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/team/{teamId}/completion")
    @Operation(summary = "Get the completion percentage for a team")
    public ResponseEntity<CompletionPercentageForTeamResponse> getCompletionPercentageForTeam(
            @PathVariable @Parameter(description = "Team ID", example = "1") int teamId
    ) {
        double completionPercentage = teamTaskService.getCompletionPercentageForTeam(teamId);
        return new ResponseEntity<>(new CompletionPercentageForTeamResponse(teamId, completionPercentage), HttpStatus.OK);
    }

    @GetMapping("/team/{teamId}/user/{userId}/completion")
    @Operation(summary = "Get the completion percentage for a user in a team")
    public ResponseEntity<CompletionPercentageForUserInTeamResponse> getCompletionPercentageForUserInTeam(
            @PathVariable @Parameter(description = "Team ID", example = "1") int teamId,
            @PathVariable @Parameter(description = "User ID", example = "1") String userId
    ) {
        double completionPercentage = teamTaskService.getCompletionPercentageForUserInTeam(teamId, userId);
        return new ResponseEntity<>(new CompletionPercentageForUserInTeamResponse(userId, teamId, completionPercentage), HttpStatus.OK);
    }
}
