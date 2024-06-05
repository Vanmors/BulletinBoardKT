package com.example.demo.controller

import com.example.demo.dto.NewTopicWithMessageDTO
import com.example.demo.entity.Topic;
import com.example.demo.repository.TopicRepository;
import com.example.demo.service.TopicService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/topic")
class TopicController @Autowired constructor(
    private val topicRepository: TopicRepository,
    private val topicService: TopicService
) {

    @GetMapping
    fun getTopics(): MutableList<Topic> {
        return topicRepository.findAll()
    }

    @PostMapping
    fun createTopicWithMessage(@RequestBody newTopicWithMessageDTO: NewTopicWithMessageDTO): ResponseEntity<Topic> {
        val createdTopic = topicService.createTopicWithMessage(newTopicWithMessageDTO)
        return ResponseEntity(createdTopic, HttpStatus.CREATED)
    }

    @DeleteMapping("{topicId}")
    fun deleteTopicWithMessage(@PathVariable topicId: Long): HttpStatus {
        topicService.deleteTopicWithMessages(topicId)
        return HttpStatus.OK
    }

}
