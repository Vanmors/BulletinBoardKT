package com.example.demo.service

import com.example.demo.dto.MessageDTO
import com.example.demo.entity.Message
import com.example.demo.entity.Topic
import com.example.demo.repository.MessageRepository
import com.example.demo.repository.TopicRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class MessageService(
    @Autowired private val topicRepository: TopicRepository,
    @Autowired private val messageRepository: MessageRepository
) {

    @Transactional
    fun createMessage(messageDTO: MessageDTO, topicId: Long): Message? {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val authorName: String = authentication.name

        val optionalTopic = topicRepository.findById(topicId)
        if (optionalTopic.isEmpty) {
            return null
        }

        val topic: Topic = optionalTopic.get()
        val message = Message(
            authorName = authorName,
            text = messageDTO.text,
            createdAt = LocalDateTime.now(),
            topic = topic
        )

        return messageRepository.save(message)
    }

    @Transactional
    @Throws(AccessDeniedException::class)
    fun deleteMessage(topicId: Long, messageId: Long) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val authorName: String = authentication.name

        val optionalTopic = topicRepository.findById(topicId)
        if (optionalTopic.isEmpty) {
            return
        }

        val topic: Topic = optionalTopic.get()
        val optionalMessage = topic.messages.firstOrNull { it.id == messageId } ?: return

        if (optionalMessage.authorName != authorName && getCurrentUserRoles().firstOrNull() != "ROLE_ADMIN") {
            throw AccessDeniedException("Access is denied")
        }

//        topic.messages.remove(optionalMessage)
        topicRepository.save(topic)
        messageRepository.delete(optionalMessage)
    }

    @Transactional
    @Throws(AccessDeniedException::class)
    fun editMessage(topicId: Long, messageId: Long, messageDTO: MessageDTO) {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val authorName: String = authentication.name

        val optionalTopic = topicRepository.findById(topicId)
        if (optionalTopic.isEmpty) {
            return
        }

        val topic: Topic = optionalTopic.get()
        val optionalMessage = topic.messages.firstOrNull { it.id == messageId } ?: return

        if (optionalMessage.authorName != authorName && getCurrentUserRoles().firstOrNull() != "ROLE_ADMIN") {
            throw AccessDeniedException("Access is denied")
        }

        optionalMessage.text = messageDTO.text
        messageRepository.save(optionalMessage)
    }

    private fun getCurrentUserRoles(): List<String> {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        val authorities: Collection<out GrantedAuthority> = authentication.authorities
        return authorities.map { it.authority }
    }
}
