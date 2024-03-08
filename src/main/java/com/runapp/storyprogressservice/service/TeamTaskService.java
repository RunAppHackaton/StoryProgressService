package com.runapp.storyprogressservice.service;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import com.runapp.storyprogressservice.repository.TeamTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamTaskService {


    private final TeamTaskRepository teamTaskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamTaskService.class);

    public TeamTaskService(TeamTaskRepository teamTaskRepository) {
        this.teamTaskRepository = teamTaskRepository;
    }

    public TeamTaskModel createTeamTask(TeamTaskModel teamTask) {
        LOGGER.info(String.format("TeamTask add: %s", teamTask));
        return teamTaskRepository.save(teamTask);
    }

    public List<TeamTaskModel> getAllTeamTasks() {
        LOGGER.info("TeamTask get all");
        return teamTaskRepository.findAll();
    }

    public Optional<TeamTaskModel> getTeamTaskById(String taskId) {
        LOGGER.info(String.format("TeamTask get by id: %s", taskId));
        return teamTaskRepository.findById(taskId);
    }

    public TeamTaskModel updateTeamTask(TeamTaskModel teamTask) {
        LOGGER.info(String.format("TeamTask update: %s", teamTask));
        return teamTaskRepository.save(teamTask);
    }

    public void deleteTeamTask(String taskId) {
        LOGGER.info(String.format("TeamTask delete by id: %s", taskId));
        teamTaskRepository.deleteById(taskId);
    }

    public double getCompletionPercentageForTeam(int teamId) {
        LOGGER.info(String.format("Get completion percentage for TeamTask by id: %s", teamId));
        List<TeamTaskModel> tasks = teamTaskRepository.findByTeamId(teamId);
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(TeamTaskModel::getDone).count();
        return totalTasks == 0 ? 0.0 : (completedTasks * 100.0 / totalTasks);
    }

    public double getCompletionPercentageForUserInTeam(int teamId, String userId) {
        LOGGER.info(String.format("Get completion percentage of TeamTask for User by {teamId=%s, userId=%s}", teamId, userId));
        List<TeamTaskModel> tasks = teamTaskRepository.findByTeamIdAndUserId(teamId, userId);
        long totalTasks = tasks.size();
        long completedTasks = tasks.stream().filter(TeamTaskModel::getDone).count();
        return totalTasks == 0 ? 0.0 : (completedTasks * 100.0 / totalTasks);
    }
}
