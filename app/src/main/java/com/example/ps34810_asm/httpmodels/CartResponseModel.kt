package com.example.ps34810_asm.httpmodels

import com.fasterxml.jackson.annotation.JsonProperty

data class Product(
    @JsonProperty("deleted") val deleted: Boolean?,
    @JsonProperty("_id") val id: String?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("price") val price: Float?,
    @JsonProperty("quantity") val quantity: Int?,
    @JsonProperty("image") val image: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("category_id") val categoryId: String?
)

data class CartItem(
    @JsonProperty("product") val product: Product?,
    @JsonProperty("quantity") val quantity: Int?,
    @JsonProperty("_id") val id: String?
)

data class CartModel(
    @JsonProperty("_id") val id: String?,
    @JsonProperty("user") val user: String?,
    @JsonProperty("totalAmount") val totalAmount: Double?,
    @JsonProperty("items") val items: List<CartItem>?,
    @JsonProperty("__v") val version: Int?
)

data class CartResponseModel(
    @JsonProperty("status") val status: Boolean,
    @JsonProperty("cart") val cart: CartModel
)
