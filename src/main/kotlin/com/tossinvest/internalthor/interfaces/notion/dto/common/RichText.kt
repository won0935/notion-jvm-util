package com.tossinvest.internalthor.interfaces.notion.dto.common

import notion.api.v1.model.common.RichTextLinkType
import notion.api.v1.model.common.RichTextType
import notion.api.v1.model.pages.PageProperty

data class RichText
constructor(
    val type: RichTextType = RichTextType.Text,
    var text: Text? = null,
    var annotations: Annotations? = null,
    var plainText: String? = null,
    var href: String? = null,
) {

    data class Text constructor(var content: String? = null, var link: Link? = null) {
        companion object {
            fun of(text: PageProperty.RichText.Text): Text {
                return Text(
                    content = text.content,
                    link = text.link?.let { Link.of(it) }
                )
            }
        }
    }

    data class Link
    constructor(var type: RichTextLinkType? = null, var url: String? = null) {
        companion object {
            fun of(link: PageProperty.RichText.Link): Link {
                return Link(
                    type = link.type,
                    url = link.url
                )
            }
        }
    }

    data class Annotations
    constructor(
        var bold: Boolean? = null,
        var italic: Boolean? = null,
        var strikethrough: Boolean? = null,
        var underline: Boolean? = null,
        var code: Boolean? = null,
        var color: RichTextColor? = null,
    ) {
        companion object {
            fun of(annotations: PageProperty.RichText.Annotations): Annotations {
                return Annotations(
                    bold = annotations.bold,
                    italic = annotations.italic,
                    strikethrough = annotations.strikethrough,
                    underline = annotations.underline,
                    code = annotations.code,
                    color = annotations.color?.let { RichTextColor.of(it) }
                )
            }
        }
    }

    companion object {
        fun of(richText: PageProperty.RichText): RichText {
            return RichText(
                type = richText.type,
                text = richText.text?.let { Text.of(it) },
                annotations = richText.annotations?.let { Annotations.of(it) },
                plainText = richText.plainText,
                href = richText.href
            )
        }
    }

}