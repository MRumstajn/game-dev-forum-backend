package com.mrumstajn.gamedevforum.conversation.repository;

import com.mrumstajn.gamedevforum.conversation.entity.Conversation;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Conversation, Long>, SearchExecutor<Conversation> {

    @Query("""
                    SELECT c FROM Conversation c WHERE c.participantA.id = :userId OR c.participantB.id = :userId
            """)
    Page<Conversation> findAllByCurrentUser(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT COUNT(c) > 0 FROM Conversation c WHERE c.id = :id AND (c.participantA.id = :participantId OR c.participantB.id = :participantId)
            """)
    Boolean existsByAnyParticipant(@Param("id") Long id, @Param("participantId") Long participantId);

    @Query("""
                    SELECT c FROM Conversation c WHERE (c.participantA.id = :participantAId AND c.participantB.id = :participantBId) OR
                    (c.participantB.id = :participantAId AND c.participantA.id = :participantBId)
            """)
    Conversation findFirstByParticipantAIdAndParticipantBId(@Param("participantAId") Long participantAId, @Param("participantBId") Long ParticipantBId);
}
