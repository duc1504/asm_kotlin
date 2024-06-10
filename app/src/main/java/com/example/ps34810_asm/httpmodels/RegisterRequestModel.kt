package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequestModel(
    @JsonProperty ("name") val name: String,
    @JsonProperty ("email") val email: String,
    @JsonProperty ("password") val password: String

)
