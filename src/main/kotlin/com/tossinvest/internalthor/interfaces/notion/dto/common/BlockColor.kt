package com.tossinvest.internalthor.interfaces.notion.dto.common

import com.google.gson.annotations.SerializedName

enum class BlockColor constructor(val value: String) {
  @SerializedName("default") Default("default"),
  @SerializedName("blue") Blue("blue"),
  @SerializedName("red") Red("red"),
  @SerializedName("gray_background") GrayBackground("gray_background")
  ;

  override fun toString(): String = value

  companion object {
    fun of(blockColor: notion.api.v1.model.common.BlockColor): BlockColor {
      return if (values().any { it.name == blockColor.name }) valueOf(blockColor.name)
      else Default
    }
  }
}
