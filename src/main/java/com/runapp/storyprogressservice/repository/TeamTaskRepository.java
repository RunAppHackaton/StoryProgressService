package com.runapp.storyprogressservice.repository;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamTaskRepository extends JpaRepository<TeamTaskModel, Integer> {

    @Query("SELECT (SUM(tt.done) * 100.0 / COUNT(tt)) FROM TeamTaskModel tt WHERE tt.team_id = :teamId")
    double getCompletionPercentageForTeam(@Param("teamId") int teamId);

    @Query("SELECT (SUM(tt.done) * 100.0 / COUNT(tt)) FROM TeamTaskModel tt WHERE tt.team_id = :teamId AND tt.userId = :userId")
    double getCompletionPercentageForTeam(@Param("teamId") int teamId, @Param("userId") int userId);
}

