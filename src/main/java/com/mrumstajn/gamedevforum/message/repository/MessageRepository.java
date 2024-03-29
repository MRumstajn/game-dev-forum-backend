package com.mrumstajn.gamedevforum.message.repository;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import com.mrumstajn.gamedevforum.message.entity.Message;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, SearchExecutor<Message> {
    Long countAllByConversationIdAndDeletedFalseAndReadersNotContaining(Long conversationId, ForumUser reader);

    @Query("""
                    SELECT m FROM Message m WHERE m.conversation.id = :conversationId AND
                    m.creationDateTime = (SELECT MAX (m2.creationDateTime) FROM Message m2 WHERE m2.conversation.id = :conversationId)
            """)
    Message getTopByConversationId(@Param("conversationId") Long conversationId);

    List<Message> getAllByConversationId(Long conversationId);
}
