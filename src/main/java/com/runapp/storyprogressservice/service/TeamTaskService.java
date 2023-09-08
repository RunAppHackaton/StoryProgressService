package com.runapp.storyprogressservice.service;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import com.runapp.storyprogressservice.repository.TeamTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamTaskService {

    private final TeamTaskRepository teamTaskRepository;

    @Autowired
    public TeamTaskService(TeamTaskRepository teamTaskRepository) {
        this.teamTaskRepository = teamTaskRepository;
    }

    // Метод для создания новой задачи
    public TeamTaskModel createTeamTask(TeamTaskModel teamTask) {
        return teamTaskRepository.save(teamTask);
    }

    // Метод для получения всех задач
    public List<TeamTaskModel> getAllTeamTasks() {
        return teamTaskRepository.findAll();
    }

    // Метод для получения задачи по идентификатору
    public Optional<TeamTaskModel> getTeamTaskById(int taskId) {
        return teamTaskRepository.findById(taskId);
    }

    // Метод для обновления задачи
    public TeamTaskModel updateTeamTask(TeamTaskModel teamTask) {
        return teamTaskRepository.save(teamTask);
    }

    // Метод для удаления задачи по идентификатору
    public void deleteTeamTask(int taskId) {
        teamTaskRepository.deleteById(taskId);
    }

    // Метод для получения процента выполненных заданий для определенной команды
    public double getCompletionPercentageForTeam(int teamId) {
        return teamTaskRepository.getCompletionPercentageForTeam(teamId);
    }

    // Метод для получения процента выполненных заданий для определенного пользователя в команде
    public double getCompletionPercentageForUserInTeam(int teamId, int userId) {
        return teamTaskRepository.getCompletionPercentageForTeam(teamId, userId);
    }
}
