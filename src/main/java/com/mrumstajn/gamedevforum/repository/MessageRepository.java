package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.Message;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long>, SearchExecutor<Message> {
    Long countAllByConversationIdAndReadersNotContaining(Long conversationId, ForumUser reader);

    @Query("""
                    SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND
                    m.creationDateTime = (SELECT MAX (m2.creationDateTime) FROM Message m2)
            """)
    Message getTopByCreationDateAndConversationId(@Param("conversationId") Long conversationId);
}
