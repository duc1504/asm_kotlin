package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class Productmodel(
    @JsonProperty("_id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: Float?,
    @JsonProperty("quantity") val quantity: Int?,
    @JsonProperty("image") val image: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("category_id") val categoryId: String?,
    @JsonProperty("deleted") val deleted: Boolean?,
)

data class ProductResponseModel(
    @JsonProperty("status") val status: Boolean,
    @JsonProperty("data") val data: List<Productmodel>
)




