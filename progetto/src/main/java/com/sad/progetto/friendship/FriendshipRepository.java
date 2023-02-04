package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Boolean existsByAppUser1AndAppUser2(AppUser first,AppUser second);

    List<Friendship> findByAppUser1(AppUser appUser);
    List<Friendship> findByAppUser2(AppUser appUser);
    @Query(value="SELECT * FROM public.friendship WHERE (app_user1_id = ?1 AND app_user2_id = ?2) OR (app_user1_id = ?2 AND app_user2_id = ?1)", nativeQuery = true)
    Friendship findByAppUser1AndAppUser2(Long appuser1_id, Long appuser2_id);

}
