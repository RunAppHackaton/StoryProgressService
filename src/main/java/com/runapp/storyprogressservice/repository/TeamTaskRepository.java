package com.runapp.storyprogressservice.repository;

import com.runapp.storyprogressservice.model.TeamTaskModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TeamTaskRepository extends MongoRepository<TeamTaskModel, String> {

//    @Query("SELECT (SUM(tt.done) * 100.0 / COUNT(tt)) FROM TeamTaskModel tt WHERE tt.team_id = :teamId")
//    double getCompletionPercentageForTeam(@Param("teamId") int teamId);
//
//    @Query("SELECT (SUM(tt.done) * 100.0 / COUNT(tt)) FROM TeamTaskModel tt WHERE tt.team_id = :teamId AND tt.userId = :userId")
//    double getCompletionPercentageForTeam(@Param("teamId") int teamId, @Param("userId") int userId);

    @Query("{ 'team_id' : ?0 }")
    List<TeamTaskModel> findByTeamId(int teamId);

    @Query("{ 'team_id' : ?0, 'user_id' : ?1 }")
    List<TeamTaskModel> findByTeamIdAndUserId(int teamId, String userId);
}

