package com.sad.progetto.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(value="SELECT organized_events_id FROM public.app_user_organized_events WHERE app_user_id=(SELECT id FROM public.app_user WHERE email=?1)", nativeQuery = true)
    List<Long> findAllOrganizedEventsFromEmail(String email);

    Event findEventById(Long idEvent);

}
