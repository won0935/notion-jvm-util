package com.tossinvest.internalthor.interfaces.notion.dto.common

import com.google.gson.annotations.SerializedName

enum class BlockType constructor(val value: String) {
    @SerializedName("paragraph")
    Paragraph("paragraph"),

    @SerializedName("heading_1")
    HeadingOne("heading_1"),

    @SerializedName("heading_2")
    HeadingTwo("heading_2"),

    @SerializedName("bulleted_list_item")
    BulletedListItem("bulleted_list_item"),

    @SerializedName("numbered_list_item")
    NumberedListItem("numbered_list_item"),

    @SerializedName("callout")
    Callout("callout"),

    @SerializedName("divider")
    Divider("divider"),

    @SerializedName("quote")
    Quote("quote"),

    @SerializedName("toggle")
    Toggle("toggle"),

    @SerializedName("image")
    Image("image"),

    @SerializedName("unsupported")
    Unsupported("unsupported");

    override fun toString(): String = value

    companion object {
        fun of(blockType: notion.api.v1.model.blocks.BlockType): BlockType {
            return if (values().any { it.name == blockType.name }) valueOf(blockType.name)
            else Unsupported
        }
    }
}