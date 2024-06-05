package com.example.demo.service

import com.example.demo.dto.NewTopicWithMessageDTO
import com.example.demo.entity.Message
import com.example.demo.entity.Topic
import com.example.demo.repository.MessageRepository
import com.example.demo.repository.TopicRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class TopicService(
    private val topicRepository: TopicRepository,
    private val messageRepository: MessageRepository
) {

    @Transactional
    fun createTopicWithMessage(newTopicWithMessageDTO: NewTopicWithMessageDTO): Topic {
        val topic = Topic(title = newTopicWithMessageDTO.topicTitle)
        val createdTopic = topicRepository.save(topic)

        val message = Message(
            authorName = newTopicWithMessageDTO.authorName,
            text = newTopicWithMessageDTO.text,
            createdAt = LocalDateTime.now(),
            topic = createdTopic
        )
        messageRepository.save(message)

        return createdTopic
    }

    @Transactional
    @Throws(ChangeSetPersister.NotFoundException::class)
    fun deleteTopicWithMessages(topicId: Long) {
        val optionalTopic = topicRepository.findById(topicId)
        if (optionalTopic.isPresent) {
            val topic = optionalTopic.get()

            val messages = topic.messages

            messageRepository.deleteAll(messages)
            topicRepository.delete(topic)
        } else {
            throw ChangeSetPersister.NotFoundException()
        }
    }
}