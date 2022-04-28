package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.google.gson.annotations.SerializedName
import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.HeadingOneBlock
import java.util.*

open class HeadingOneBlockDto
constructor(
    override val type: BlockType = BlockType.HeadingOne,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    @SerializedName("heading_1") val heading1: Element,
) : BlockDto {


    open class Element {
        companion object {
            fun of(element: HeadingOneBlock.Element): Element {
                return Element()
            }
        }
    }

    companion object {
        fun of(block: Block): BlockDto {
            return HeadingOneBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                heading1 = Element.of(block.asHeadingOne().heading1)
            )
        }
    }
}
