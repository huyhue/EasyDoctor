package fpt.edu.vn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fpt.edu.vn.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {

}
