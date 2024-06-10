package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class CheckoutResponseModel(
    @JsonProperty ("status") val status: Boolean,
)
