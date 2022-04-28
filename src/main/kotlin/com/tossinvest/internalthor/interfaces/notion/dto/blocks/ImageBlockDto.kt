package com.tossinvest.internalthor.interfaces.notion.dto.blocks

import com.tossinvest.internalthor.interfaces.notion.dto.common.BlockType
import notion.api.v1.model.blocks.Block
import notion.api.v1.model.blocks.ImageBlock
import java.util.*

open class ImageBlockDto
constructor(
    override val type: BlockType = BlockType.Image,
    override var id: String? = UUID.randomUUID().toString(),
    override var hasChildren: Boolean? = null,
    val image: Element? = null,
) : BlockDto {

    open class Element
    constructor(
        val type: String? = null,
        val external: External? = null,
        var file: File? = null,
        var caption: List<String>? = emptyList(),
    ) {
        companion object {
            fun of(element: ImageBlock.Element): Element {
                return Element(
                    type = element.type,
                    external = element.external?.let { External.of(it) },
                    file = element.file?.let { File.of(it) },
                    caption = element.caption
                )
            }
        }
    }

    open class External
    constructor(
        val url: String? = null,
    ) {
        companion object {
            fun of(external: ImageBlock.External): External {
                return External(
                    url = external.url
                )
            }
        }
    }

    open class File
    constructor(
        val url: String? = null,
        var expiryTime: String? = null,
    ) {
        companion object {
            fun of(file: ImageBlock.File): File {
                return File(
                    url = file.url,
                    expiryTime = file.expiryTime
                )
            }
        }
    }

    companion object{
        fun of(block: Block): BlockDto {
            return ImageBlockDto(
                type = BlockType.of(block.type),
                id = block.id,
                hasChildren = block.hasChildren,
                image = block.asImage().image?.let { Element.of(it) }
            )
        }
    }
}
