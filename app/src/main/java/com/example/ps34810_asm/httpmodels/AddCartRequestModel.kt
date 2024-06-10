package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class AddCartRequestModel(
    @JsonProperty("userId") val userId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int
)
