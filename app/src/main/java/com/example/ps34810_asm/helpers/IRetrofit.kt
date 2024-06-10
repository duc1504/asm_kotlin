package com.example.ps34810_asm.helpers


import android.util.Log
import com.example.ps34810_asm.httpmodels.AddCartRequestModel
import com.example.ps34810_asm.httpmodels.AddCartResponseModel
import com.example.ps34810_asm.httpmodels.CartResponseModel
import com.example.ps34810_asm.httpmodels.CheckoutResponseModel
import com.example.ps34810_asm.httpmodels.DeleteResponseModel
import com.example.ps34810_asm.httpmodels.LoginRequestModel
import com.example.ps34810_asm.httpmodels.LoginResponseModel
import com.example.ps34810_asm.httpmodels.ProductDetailResponseModel
import com.example.ps34810_asm.httpmodels.ProductResponseModel
import com.example.ps34810_asm.httpmodels.RegisterRequestModel
import com.example.ps34810_asm.httpmodels.RegisterResponseModel
import com.example.ps34810_asm.httpmodels.UpdateCartRequestModel
import com.example.ps34810_asm.httpmodels.UpdateCartResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IRetrofit {
    // khai báo các api
    // đăng kí tài khoản
    @POST("register")
    fun register(@Body body: RegisterRequestModel): Call<RegisterResponseModel>
    // đăng nhập tài khoản
    @POST("login")
    fun login(@Body body: LoginRequestModel): Call<LoginResponseModel>
    // lấy danh sách sản phẩm
    @GET("products")
    fun getProducts(): Call<ProductResponseModel>

    // lấy chi tiết sản phẩm
    @GET("detail/{id}")
    fun getProductDetail(@Path("id") id: String?): Call<ProductDetailResponseModel>
    // lấy giỏ hàng
    @GET("cart/{id}")
    fun getCart(@Path("id") id: String?): Call<CartResponseModel>
    // thêm vào giỏ hàng
    @POST("cart/add")
    fun addCart(@Body body: AddCartRequestModel): Call<AddCartResponseModel>

    // cập nhật giỏ hàng
        // Khai báo API cập nhật số lượng sản phẩm trong giỏ hàng
        @PUT("cart/updateQuantity/{userId}/{productId}")
        fun updateCartItemQuantity(
            @Path("userId") userId: String,
            @Path("productId") productId: String,
            @Body body: UpdateCartRequestModel
        ): Call<UpdateCartResponseModel>

        // Xóa sản phẩm trong giỏ hàng
        @DELETE("cart/removeItem/{userId}/{productId}")
        fun deleteCartItem(
            @Path("userId") userId: String,
            @Path("productId") productId: String
        ): Call<DeleteResponseModel>

        // thanh toán
        @POST("checkout/{userId}")
        fun checkoutCart(@Path("userId") userId: String): Call<CheckoutResponseModel>

}