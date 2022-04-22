package com.tossinvest.internalthor.interfaces.notion

import notion.api.v1.NotionClient
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.BlockType.*
import notion.api.v1.model.blocks.Blocks
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notion")
class NotionContoller(
    val client: NotionClient = NotionClient("")
) {

    //동기화한다 //배치?

    //블록아이디로 불러온다?
    @GetMapping("/blocks/{blockId}")
    fun getblocks(@PathVariable blockId: String) {
        getContentsPage(blockId)
    }


    private fun getContentsPage(blockId: String): Blocks {
        val item = client.retrieveBlockChildren(blockId)
        return getFullBlocks(blockId, item)
    }

    private fun getFullBlocks(blockId: String, blocks: Blocks): Blocks {
        return if (blocks.hasMore) {
            val nextItem: Blocks = client.retrieveBlockChildren(
                blockId = blockId,
                startCursor = blocks.nextCursor
            )

            val mergedItem : MutableList<Block> = blocks.results.toMutableList()
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
            Paragraph -> block.asParagraph().paragraph.children = children
            BulletedListItem -> block.asBulletedListItem().bulletedListItem.children = children
            NumberedListItem -> block.asNumberedListItem().numberedListItem.children = children
            Toggle -> block.asToggle().toggle.children = children
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