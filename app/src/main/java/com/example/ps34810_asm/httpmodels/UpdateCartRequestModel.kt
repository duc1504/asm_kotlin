package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCartRequestModel(
    @JsonProperty ("quantity") val quantity: Int
)
