package com.runapp.storyprogressservice.controller.services;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import com.runapp.storyprogressservice.repository.TeamTaskRepository;
import com.runapp.storyprogressservice.service.TeamTaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TeamTaskServiceTest {

    @Mock
    private TeamTaskRepository teamTaskRepository;

    @InjectMocks
    private TeamTaskService teamTaskService;

    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTeamTask() {
        TeamTaskModel teamTask = new TeamTaskModel();
        when(teamTaskRepository.save(any(TeamTaskModel.class))).thenReturn(teamTask);
        TeamTaskModel createdTask = teamTaskService.createTeamTask(teamTask);
        assertEquals(teamTask, createdTask);
        verify(teamTaskRepository, times(1)).save(teamTask);
    }

    @Test
    void testGetAllTeamTasks() {
        List<TeamTaskModel> tasks = List.of(new TeamTaskModel(), new TeamTaskModel());
        when(teamTaskRepository.findAll()).thenReturn(tasks);
        List<TeamTaskModel> retrievedTasks = teamTaskService.getAllTeamTasks();
        assertEquals(tasks, retrievedTasks);
        verify(teamTaskRepository, times(1)).findAll();
    }

    @Test
    void testGetTeamTaskById() {
        String taskId = "taskId";
        TeamTaskModel task = new TeamTaskModel();
        when(teamTaskRepository.findById(taskId)).thenReturn(Optional.of(task));
        Optional<TeamTaskModel> retrievedTask = teamTaskService.getTeamTaskById(taskId);
        assertTrue(retrievedTask.isPresent());
        assertEquals(task, retrievedTask.get());
        verify(teamTaskRepository, times(1)).findById(taskId);
    }

    @Test
    void testUpdateTeamTask() {
        TeamTaskModel teamTask = new TeamTaskModel();
        when(teamTaskRepository.save(any(TeamTaskModel.class))).thenReturn(teamTask);
        TeamTaskModel updatedTask = teamTaskService.updateTeamTask(teamTask);
        assertEquals(teamTask, updatedTask);
        verify(teamTaskRepository, times(1)).save(teamTask);
    }

    @Test
    void testDeleteTeamTask() {
        String taskId = "taskId";
        teamTaskService.deleteTeamTask(taskId);
        verify(teamTaskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void testGetCompletionPercentageForTeam() {
        TeamTaskModel teamTask1 = new TeamTaskModel();
        TeamTaskModel teamTask2 = new TeamTaskModel();
        teamTask1.setDone(true);
        teamTask2.setDone(false);

        // Mock data
        List<TeamTaskModel> tasks = List.of(teamTask1, teamTask2);
        int teamId = 123;

        when(teamTaskRepository.findByTeamId(teamId)).thenReturn(tasks);

        double completionPercentage = teamTaskService.getCompletionPercentageForTeam(teamId);
        assertEquals(50.0, completionPercentage);
    }

    @Test
    void testGetCompletionPercentageForUserInTeam() {
        // Mock data
        TeamTaskModel teamTask1 = new TeamTaskModel();
        TeamTaskModel teamTask2 = new TeamTaskModel();
        teamTask1.setDone(true);
        teamTask2.setDone(false);

        // Mock data
        List<TeamTaskModel> tasks = List.of(teamTask1, teamTask2);
        int teamId = 123;
        String userId = "456";

        when(teamTaskRepository.findByTeamIdAndUserId(teamId, userId)).thenReturn(tasks);

        double completionPercentage = teamTaskService.getCompletionPercentageForUserInTeam(teamId, userId);
        assertEquals(50.0, completionPercentage);
    }
}

