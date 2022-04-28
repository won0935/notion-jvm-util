package com.tossinvest.internalthor.domain.notion

import com.tossinvest.internalthor.interfaces.notion.dto.blocks.BlockDto
import com.tossinvest.internalthor.interfaces.notion.dto.contents.ContentsDto
import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.BlockType
import notion.api.v1.model.blocks.Blocks
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable

@Component
class NotionContentsGenerator(
    val client: NotionClient = NotionClient("")
) {

    fun getContents(@PathVariable pageId: String): ContentsDto {
        val page = client.retrievePage(pageId)
        val blocks = getBlockList(pageId)
        return ContentsDto.of(page, blocks)
    }

    private fun getBlockList(blockId: String): List<BlockDto> {
        val item = client.retrieveBlockChildren(blockId)
        return getFullBlocks(blockId, item).results.map { BlockDto.of(it) }
    }

    private fun getFullBlocks(blockId: String, blocks: Blocks): Blocks {
        return if (blocks.hasMore) {
            val nextItem: Blocks = client.retrieveBlockChildren(
                blockId = blockId,
                startCursor = blocks.nextCursor
            )

            val mergedItem: MutableList<Block> = blocks.results.toMutableList()
            mergedItem.addAll(nextItem.results)

            mergedItem.forEach {
                if (it.hasChildren == true) {
                    recurseBlock(it)
                }
            }

            nextItem.copy(results = mergedItem)
        } else {
            blocks.results.forEach {
                if (it.hasChildren == true) {
                    recurseBlock(it)
                }
            }
            blocks
        }
    }

    private fun recurseBlock(block: Block) {
        if (block.hasChildren == true) {
            val children = getFullBlocks(block.id!!, client.retrieveBlockChildren(block.id!!)).results
            setElement(block, children)

            children.forEach {
                if (it.hasChildren == true) {
                    recurseBlock(it)
                }
            }
        }
    }

    private fun setElement(block: Block, children: List<Block>) {
        when (block.type) {
            BlockType.Paragraph -> block.asParagraph().paragraph.children = children
            BlockType.BulletedListItem -> block.asBulletedListItem().bulletedListItem.children = children
            BlockType.NumberedListItem -> block.asNumberedListItem().numberedListItem.children = children
            BlockType.Toggle -> block.asToggle().toggle.children = children
//             Quote -> block.asQuote().quote.children = children
//             Callout -> block.asCallout().callout.children = children
//             Synced -> block.asSyncedBlock().syncedBlock.children = children
//             Template -> block.asTemplate().template.children = children
//             Column -> block.asColumn().column.children = children
//             Table -> block.asTable().table.children = children
            else -> throw IllegalStateException("Failed to cast ${block.type} block to Element")
        }
    }

}