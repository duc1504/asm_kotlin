package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteResponseModel(
    @JsonProperty ("status") val status: Boolean
)
