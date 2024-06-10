package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponseModel(
    @JsonProperty("status") val status :Boolean,
    @JsonProperty("email") val email :String,
    @JsonProperty("id") val id :String
)
