package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.DividerBlock
import java.util.*

open class DividerBlockDto
constructor(
    override val type: BlockType = BlockType.Divider,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    val divider: Element? = null,
) : BlockDto {

    open class Element {
        companion object {
            fun of(element: DividerBlock.Element): Element {
                return Element()
            }
        }
    }

    companion object {
        fun of(block: Block): DividerBlockDto {
            return DividerBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                divider = block.asDivider().divider?.let { Element.of(it) }
            )
        }
    }
}
