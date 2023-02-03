package com.sad.progetto.friendship;

import com.sad.progetto.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Boolean existsByAppUser1AndAppUser2(AppUser first,AppUser second);

    List<Friendship> findByAppUser1(AppUser appUser);
    List<Friendship> findByAppUser2(AppUser appUser);

}
