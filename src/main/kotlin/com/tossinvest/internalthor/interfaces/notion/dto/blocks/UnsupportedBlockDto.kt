package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import notion.api.v1.model.blocks.UnsupportedBlock
import java.util.*

open class UnsupportedBlockDto
constructor(
    override val type: BlockType = BlockType.Unsupported,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    val unsupported: Element? = null,
) : BlockDto {

    open class Element {
        companion object {
            fun of(element: UnsupportedBlock.Element): Element {
                return Element()
            }
        }
    }
}
