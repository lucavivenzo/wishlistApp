package com.sad.progetto.present;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentRepository extends JpaRepository<Present, Long> {
}
