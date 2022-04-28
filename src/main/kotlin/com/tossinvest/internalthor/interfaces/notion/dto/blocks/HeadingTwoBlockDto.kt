package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.google.gson.annotations.SerializedName
import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockColor
import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import com.tossinvest.internalthor.interfaces.notion.dto.common.RichText
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.HeadingTwoBlock
import java.util.*

open class HeadingTwoBlockDto
constructor(
    override val type: BlockType = BlockType.HeadingTwo,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    @SerializedName("heading_2") val heading2: Element,
) : BlockDto {

    open class Element(var richText: List<RichText>, var color: BlockColor? = null) {
        companion object {
            fun of(element: HeadingTwoBlock.Element): Element {
                return Element(
                    richText = element.richText.map { RichText.of(it) },
                    color = element.color?.let { BlockColor.of(it) }
                )
            }
        }
    }

    companion object {
        fun of(block: Block): BlockDto {
            return HeadingTwoBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                heading2 = Element.of(block.asHeadingTwo().heading2)
            )
        }
    }
}
