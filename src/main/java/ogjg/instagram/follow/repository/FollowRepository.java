package ogjg.instagram.follow.repository;

import ogjg.instagram.follow.domain.Follow;
import ogjg.instagram.follow.response.FollowResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {


//  내가 팔로우 한놈들
    @Query("select count(f) from Follow f where f.followPK.userId = :userId")
    Long followingCount(@Param("userId") Long userId);

//    todo join해서 프로필 정보넘기기
    @Query("select new ogjg.instagram.follow.response.FollowResponse(f.followPK.userId, f.followPK.followId) from Follow f where f.followPK.userId = :userId")
    List<FollowResponse> FollowingList(@Param("userId")Long userId);

//  날 팔로우 한놈들
    @Query("select count(f) from Follow f where f.followPK.followId = :userId")
    Long followerCount(@Param("userId")Long userId);

//    todo join해서 프로필 정보넘기기
    @Query("select new ogjg.instagram.follow.response.FollowResponse(f.followPK.userId, f.followPK.followId) from Follow f where f.followPK.followId = :userId")
    List<FollowResponse> followerList(@Param("userId") Long userId);

    @Query("select new ogjg.instagram.follow.response.FollowResponse(f.followPK.userId, f.followPK.followId) from Follow f where f.followPK.userId = :userId and f.followPK.followId = :followId")
    FollowResponse followerMeToo(@Param("userId") Long userId, @Param("followId") Long followId);

}
