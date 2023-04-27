package com.mrumstajn.gamedevforum.repository;

import com.mrumstajn.gamedevforum.entity.Conversation;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long>, SearchExecutor<Conversation> {

    Boolean existsByParticipantsIdAndId(Long participantId, Long id);
}
