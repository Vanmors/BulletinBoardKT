package com.example.demo.controller

import com.example.demo.dto.MessageDTO
import com.example.demo.entity.Message
import com.example.demo.repository.MessageRepository
import com.example.demo.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/topic")
class MessageController @Autowired constructor(
    private val messageRepository: MessageRepository,
    private val messageService: MessageService
) {

    @GetMapping("{topicId}/message")
    fun getMessage(@PathVariable topicId: Long, pageable: Pageable): ResponseEntity<Page<Message?>> {
        val messagePage = messageRepository.findByTopicId(topicId, pageable)
        if (messagePage != null) {
            return if (messagePage.isEmpty) {
                ResponseEntity(HttpStatus.NOT_FOUND)
            } else {
                ResponseEntity(messagePage, HttpStatus.OK)
            }
        }
        return ResponseEntity(messagePage, HttpStatus.BAD_REQUEST)
    }

    @PostMapping("{topicId}/message")
    fun createMessage(@PathVariable topicId: Long, @RequestBody message: MessageDTO): ResponseEntity<Message> {
        return ResponseEntity(messageService.createMessage(message, topicId), HttpStatus.CREATED)
    }

    @DeleteMapping("{topicId}/message/{messageId}")
    fun deleteMessage(@PathVariable topicId: Long, @PathVariable messageId: Long): HttpStatus {
        messageService.deleteMessage(topicId, messageId)
        return HttpStatus.OK
    }

    @PatchMapping("{topicId}/message/{messageId}")
    fun editMessage(
        @PathVariable topicId: Long,
        @PathVariable messageId: Long,
        @RequestBody messageDTO: MessageDTO
    ): HttpStatus {
        messageService.editMessage(topicId, messageId, messageDTO)
        return HttpStatus.OK
    }
}
