package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockColor
import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import com.tossinvest.internalthor.interfaces.notion.dto.common.RichText
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.ToggleBlock
import java.util.*

open class ToggleBlockDto
constructor(
    override val type: BlockType = BlockType.Toggle,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    val toggle: Element,
) : BlockDto {

    open class Element
    constructor(
        var richText: List<RichText>,
        var children: List<BlockDto>? = null,
        var color: BlockColor? = null
    ) {
        companion object {
            fun of(element: ToggleBlock.Element): Element {
                return Element(
                    richText = element.richText.map { RichText.of(it) },
                    children = element.children?.map { BlockDto.of(it) },
                    color = element.color?.let { BlockColor.of(it) }
                )
            }
        }
    }

    companion object {
        fun of(block: Block): BlockDto {
            return ToggleBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                toggle = Element.of(block.asToggle().toggle)
            )
        }
    }
}
