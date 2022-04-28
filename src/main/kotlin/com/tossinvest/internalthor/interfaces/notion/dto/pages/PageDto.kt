package com.tossinvest.internalthor.interfaces.notion.dto.pages

import notion.api.v1.model.common.Cover
import notion.api.v1.model.common.Icon
import notion.api.v1.model.users.User

data class PageDto
constructor(
    val id: String,
    val icon: Icon,
    val cover: Cover,
    val createdTime: String,
    val createdBy: User,
    val lastEditedTime: String,
    val lastEditedBy: User,
    val url: String,
)
