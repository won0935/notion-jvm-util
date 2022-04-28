package com.tossinvest.internalthor.interfaces.notion.dto.contents

import com.tossinvest.internalthor.interfaces.notion.dto.blocks.BlockDto
import com.tossinvest.internalthor.interfaces.notion.dto.common.RichText
import notion.api.v1.model.pages.Page

data class ContentsDto
constructor(
    val id: String,
    val title: List<RichText>? = null,
    val createdTime: String,
    val createdBy: String,
    val lastEditedTime: String,
    val lastEditedBy: String,
    val results: List<BlockDto>? = null,
) {
    companion object {
        fun of(page: Page, blocks: List<BlockDto>): ContentsDto {
            return ContentsDto(
                id = page.id,
                title = page.properties.getOrDefault("title", null)?.title?.map { RichText.of(it) },
                createdTime = page.createdTime,
                createdBy = page.createdBy.id,
                lastEditedBy = page.lastEditedBy.id,
                lastEditedTime = page.lastEditedTime,
                results = blocks
            )
        }
    }
}
