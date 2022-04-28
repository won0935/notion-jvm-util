package com.tossinvest.internalthor.interfaces.notion.dto.common

import com.google.gson.annotations.SerializedName

enum class RichTextColor constructor(val value: String) {
  @SerializedName("default") Default("default"),
  @SerializedName("blue") Blue("blue"),
  @SerializedName("red") Red("red"),
  @SerializedName("gray_background") GrayBackground("gray_background")
  ;

  override fun toString(): String = value

  companion object {
    fun of(richTextColor: notion.api.v1.model.common.RichTextColor): RichTextColor {
      return if (values().any { it.name == richTextColor.name }) valueOf(richTextColor.name)
      else Default
    }
  }
}
