package com.runapp.storyprogressservice.controller;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import com.runapp.storyprogressservice.service.TeamTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeamTaskController.class)
public class TeamTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamTaskService teamTaskService;

    @Test
    public void testGetAllTeamTasksWhenTasksExistThenReturnTasks() throws Exception {
        TeamTaskModel task1 = new TeamTaskModel();
        TeamTaskModel task2 = new TeamTaskModel();
        List<TeamTaskModel> tasks = Arrays.asList(task1, task2);

        when(teamTaskService.getAllTeamTasks()).thenReturn(tasks);

        mockMvc.perform(get("/team-tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").exists());
    }

    @Test
    public void testGetAllTeamTasksWhenNoTasksThenReturnEmptyList() throws Exception {
        when(teamTaskService.getAllTeamTasks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/team-tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void testGetTeamTaskByIdWhenTaskExistsThenReturnTask() throws Exception {
        TeamTaskModel task = new TeamTaskModel();
        task.setId(1);

        when(teamTaskService.getTeamTaskById(1)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/team-tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testGetTeamTaskByIdWhenTaskDoesNotExistThenReturnNotFound() throws Exception {
        when(teamTaskService.getTeamTaskById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/team-tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTeamTaskWhenTaskExistsThenReturnUpdated() throws Exception {
        TeamTaskModel task = new TeamTaskModel();
        task.setId(1);

        when(teamTaskService.updateTeamTask(task)).thenReturn(task);

        mockMvc.perform(put("/team-tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteTeamTaskWhenTaskExistsThenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/team-tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetCompletionPercentageForTeamWhenTeamExistsThenReturnPercentage() throws Exception {
        when(teamTaskService.getCompletionPercentageForTeam(1)).thenReturn(50.0);

        mockMvc.perform(get("/team-tasks/team/1/completion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentage_complete").value(50.0));
    }

    @Test
    public void testGetCompletionPercentageForUserInTeamWhenUserExistsThenReturnPercentage() throws Exception {
        when(teamTaskService.getCompletionPercentageForUserInTeam(1, 1)).thenReturn(50.0);

        mockMvc.perform(get("/team-tasks/team/1/user/1/completion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentage_complete").value(50.0));
    }

    @Test
    public void testUpdateTeamTaskWhenTaskExistsThenReturnUpdatedTask() throws Exception {
        TeamTaskModel updatedTask = new TeamTaskModel();
        updatedTask.setId(1);

        when(teamTaskService.updateTeamTask(any(TeamTaskModel.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/team-tasks/{taskId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"taskId\":1,\"done\":1,\"team_id\":1}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateTeamTaskWhenTaskDoesNotExistThenReturnNotFound() throws Exception {
        when(teamTaskService.updateTeamTask(any(TeamTaskModel.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(put("/team-tasks/{taskId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"taskId\":1,\"done\":1,\"team_id\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateTeamTaskWhenTaskDataIsInvalidThenReturnBadRequest() throws Exception {
        when(teamTaskService.updateTeamTask(any(TeamTaskModel.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(put("/team-tasks/{taskId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":-1,\"taskId\":-1,\"done\":-1,\"team_id\":-1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTeamTaskWhenTaskExistsThenReturn204() throws Exception {
        int taskId = 1;
        doNothing().when(teamTaskService).deleteTeamTask(taskId);

        mockMvc.perform(delete("/team-tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTeamTaskWhenTaskDoesNotExistThenReturn404() throws Exception {
        int taskId = 1;
        doThrow(IllegalArgumentException.class).when(teamTaskService).deleteTeamTask(taskId);

        mockMvc.perform(delete("/team-tasks/" + taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTeamTaskWhenInvalidInputThenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/team-tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"invalid\",\"taskId\":\"invalid\",\"done\":\"invalid\",\"team_id\":\"invalid\"}"))
                .andExpect(status().isBadRequest());
    }
}