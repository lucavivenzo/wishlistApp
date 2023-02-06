package com.sad.progetto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findWishlistById(Long id);
    @Query(value="SELECT wishlists_id FROM public.app_user_wishlists WHERE app_user_id=(SELECT id FROM public.app_user WHERE email=?1)", nativeQuery = true)
    List<Long> findAllOwnedWishlistsFromEmail(String email);

    @Query(value="SELECT * FROM public.wishlist WHERE owner_id=(SELECT id FROM public.app_user WHERE email=?1)", nativeQuery = true)
    List<Wishlist> getAllOwnedWishlistsFromEmail(String email);

    @Query(value = "((SELECT public.wishlist.id, public.wishlist.name, public.wishlist.description, public.wishlist.size, public.wishlist.event_id, public.wishlist.owner_id FROM public.wishlist INNER JOIN public.friendship ON wishlist.owner_id = friendship.app_user1_id WHERE public.friendship.app_user1_id NOT IN (?1) AND public.friendship.app_user2_id =?1 AND public.friendship.friendship_state=1) UNION (SELECT public.wishlist.id, public.wishlist.name, public.wishlist.description, public.wishlist.size, public.wishlist.event_id, public.wishlist.owner_id FROM public.wishlist INNER JOIN public.friendship ON public.wishlist.owner_id = public.friendship.app_user2_id WHERE public.friendship.app_user2_id NOT IN (?1) AND public.friendship.app_user1_id =?1 AND public.friendship.friendship_state=1)) ORDER BY owner_id", nativeQuery = true)
    List<Wishlist> getAllFriendsWishlistById(Long currentUserId);

}
