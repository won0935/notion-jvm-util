package com.tossinvest.internalthor.interfaces.notion

import com.tossinvest.internalthor.domain.notion.NotionContentsGenerator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notion")
class NotionContoller(
    val generator: NotionContentsGenerator,
) {

    //동기화한다 //배치?
    //블록아이디로 불러온다?
    @GetMapping("/contents/{pageId}")
    fun getContents(@PathVariable pageId: String) {
        generator.getContents(pageId)
    }
}