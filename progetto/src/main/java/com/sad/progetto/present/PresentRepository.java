package com.sad.progetto.present;

import com.sad.progetto.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresentRepository extends JpaRepository<Present, Long> {
    List<Present> findByWishlist(Wishlist wishlist);

    Present findPresentById(Long id);
}
