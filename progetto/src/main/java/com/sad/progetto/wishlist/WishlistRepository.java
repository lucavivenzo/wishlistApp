package com.sad.progetto.wishlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query(value="SELECT wishlists_id FROM public.app_user_wishlists WHERE app_user_id=(SELECT id FROM public.app_user WHERE email=?1)", nativeQuery = true)
    List<Long> findAllOwnedWishlistsFromEmail(String email);

    @Query(value="SELECT * FROM public.wishlist WHERE owner_id=(SELECT id FROM public.app_user WHERE email=?1)", nativeQuery = true)
    List<Wishlist> getAllOwnedWishlistsFromEmail(String email);
}
