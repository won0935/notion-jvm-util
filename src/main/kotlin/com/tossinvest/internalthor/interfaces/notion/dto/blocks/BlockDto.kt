package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.BlockType.*


interface BlockDto {

    val type: BlockType
    var id: String?
    var hasChildren: Boolean?

    fun asParagraph(): ParagraphBlockDto =
        if (type == BlockType.Paragraph) this as ParagraphBlockDto
        else throw IllegalStateException("Failed to cast $type block to ParagraphBlock")

    fun asHeadingOne(): HeadingOneBlockDto =
        if (type == BlockType.HeadingOne) this as HeadingOneBlockDto
        else throw IllegalStateException("Failed to cast $type block to HeadingOneBlock")

    fun asHeadingTwo(): HeadingTwoBlockDto =
        if (type == BlockType.HeadingTwo) this as HeadingTwoBlockDto
        else throw IllegalStateException("Failed to cast $type block to HeadingTwoBlock")

    fun asBulletedListItem(): BulletedListItemBlockDto =
        if (type == BlockType.BulletedListItem) this as BulletedListItemBlockDto
        else throw IllegalStateException("Failed to cast $type block to BulletedListItemBlock")

    fun asNumberedListItem(): NumberedListItemBlockDto =
        if (type == BlockType.NumberedListItem) this as NumberedListItemBlockDto
        else throw IllegalStateException("Failed to cast $type block to NumberedListItemBlock")

    fun asToggle(): ToggleBlockDto =
        if (type == BlockType.Toggle) this as ToggleBlockDto
        else throw IllegalStateException("Failed to cast $type block to ToggleBlock")

    fun asQuote(): QuoteBlockDto =
        if (type == BlockType.Quote) this as QuoteBlockDto
        else throw IllegalStateException("Failed to cast $type block to QuoteBlock")

    fun asCallout(): CalloutBlockDto =
        if (type == BlockType.Callout) this as CalloutBlockDto
        else throw IllegalStateException("Failed to cast $type block to CalloutBlock")

    fun asDivider(): DividerBlockDto =
        if (type == BlockType.Divider) this as DividerBlockDto
        else throw IllegalStateException("Failed to cast $type block to DividerBlock")

    fun asImage(): ImageBlockDto =
        if (type == BlockType.Image) this as ImageBlockDto
        else throw IllegalStateException("Failed to cast $type block to ImageBlock")

    fun asUnsupported(): UnsupportedBlockDto =
        if (type == BlockType.Unsupported) this as UnsupportedBlockDto
        else throw IllegalStateException("Failed to cast $type block to UnsupportedBlock")

    companion object {
        fun of(block: Block): BlockDto {
            return when (block.type) {
                Paragraph -> ParagraphBlockDto.of(block)
                HeadingOne -> HeadingOneBlockDto.of(block)
                HeadingTwo -> HeadingTwoBlockDto.of(block)
                BulletedListItem -> BulletedListItemBlockDto.of(block)
                NumberedListItem -> NumberedListItemBlockDto.of(block)
                Callout -> CalloutBlockDto.of(block)
                Divider -> DividerBlockDto.of(block)
                Quote -> QuoteBlockDto.of(block)
                Toggle -> ToggleBlockDto.of(block)
                Image -> ImageBlockDto.of(block)

                else -> UnsupportedBlockDto()
            }
        }
    }

}