package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockColor
import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import com.tossinvest.internalthor.interfaces.notion.dto.common.RichText
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.QuoteBlock
import java.util.*

open class QuoteBlockDto
constructor(
    override val type: BlockType = BlockType.Quote,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    val quote: Element? = null,
) : BlockDto {

    open class Element
    constructor(var richText: List<RichText>? = null, var color: BlockColor? = null) {
        companion object {
            fun of(element: QuoteBlock.Element): Element {
                return Element(
                    richText = element.richText?.map { RichText.of(it) },
                    color = element.color?.let { BlockColor.of(it) }
                )
            }
        }
    }

    companion object {
        fun of(block: Block): BlockDto {
            return QuoteBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                quote = block.asQuote().quote?.let { Element.of(it) }
            )
        }
    }
}
