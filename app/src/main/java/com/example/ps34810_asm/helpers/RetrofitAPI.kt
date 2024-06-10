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
import retrofit2.Callback
import retrofit2.Response


class RetrofitAPI {
    private val retrofit = RetrofitClient.getClient()
    private val iRetrofit = retrofit.create(IRetrofit::class.java)

    // Đăng kí
    fun register(body: RegisterRequestModel, callback: (RegisterResponseModel?) -> Unit) {
        iRetrofit.register(body).enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(
                call: Call<RegisterResponseModel>,
                response: Response<RegisterResponseModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("success", response.message())
                    val registerResponseModel = response.body()
                    callback(registerResponseModel)
                } else {
                    Log.d("error", response.message())
                    Log.d("error", response.message())
                    callback(null)
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }

    // Đăng nhập
    fun login(body: LoginRequestModel, callback: (LoginResponseModel?) -> Unit) {

        iRetrofit.login(body).enqueue(object : Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                if (response.isSuccessful) {
                    Log.d("success", response.message())
                    val loginResponseModel = response.body()
                    callback(loginResponseModel)
                } else {
                    Log.d("error", response.message())

                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }
    // Lấy danh sách sản phẩm
    fun getProducts(callback: (ProductResponseModel?) -> Unit) {
        iRetrofit.getProducts().enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) {
                if (response.isSuccessful) {
                    val productList = response.body()

                    callback(productList)
                } else {
                    Log.d("error", response.message())
                    callback(null)
                }
            }

            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }
    // lấy chi tiết sản phẩm
    fun getProductDetail( id :String?,callback: (ProductDetailResponseModel?) -> Unit){
        iRetrofit.getProductDetail(id=id).enqueue(object : Callback<ProductDetailResponseModel> {
            override fun onResponse(
                call: Call<ProductDetailResponseModel>,
                response: Response<ProductDetailResponseModel>
            ) {
            if (response.isSuccessful) {
                val productDetail = response.body()
                callback(productDetail)
            } else {
                Log.d("error", response.message())
                callback(null)
            }
            }
            override fun onFailure(call: Call<ProductDetailResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }

        })
    }

    // lấy chi tiết giỏ hàng
    fun getCartDetail(id: String?, callback: (CartResponseModel?) -> Unit){
        iRetrofit.getCart(id=id).enqueue(object : Callback<CartResponseModel> {
            override fun onResponse(
                call: Call<CartResponseModel>,
                response: Response<CartResponseModel>
            ) {
                if (response.isSuccessful) {
                    val cartDetail = response.body()
                    callback(cartDetail)
                } else {
                    Log.d("error", response.message())
                    callback(null)
                }
            }
            override fun onFailure(call: Call<CartResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }

        })
    }
    // Thêm sản phẩm vào giỏ hàng
    fun addProductToCart(body: AddCartRequestModel, callback: (AddCartResponseModel?) -> Unit){
        iRetrofit.addCart(body=body).enqueue(object : Callback<AddCartResponseModel> {
            override fun onResponse(
                call: Call<AddCartResponseModel>,
                response: Response<AddCartResponseModel>
            ) {
                if (response.isSuccessful) {
                    val cartDetail = response.body()
                    callback(cartDetail)
                } else {
                    Log.d("error", response.message())
            }
            }
            override fun onFailure(call: Call<AddCartResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }
            })
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    fun updateCartItemQuantity(userId: String, productId: String, body: UpdateCartRequestModel, callback: (UpdateCartResponseModel?) -> Unit) {
        iRetrofit.updateCartItemQuantity(userId, productId, body).enqueue(object : Callback<UpdateCartResponseModel> {
            override fun onResponse(
                call: Call<UpdateCartResponseModel>,
                response: Response<UpdateCartResponseModel>
            ) {
                if (response.isSuccessful) {
                    val updateResponse = response.body()
                    callback(updateResponse)
                } else {
                    Log.d("error", response.message())
                    callback(null)
                }
            }
            override fun onFailure(call: Call<UpdateCartResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }

    // Xóa sản phẩm trong giỏ hàng
    fun deleteCartItem(userId: String, productId: String, callback: (DeleteResponseModel) -> Unit) {
        iRetrofit.deleteCartItem(userId, productId).enqueue(object : Callback<DeleteResponseModel> {
            override fun onResponse(
                call: Call<DeleteResponseModel>,
                response: Response<DeleteResponseModel>
            ) {
                if (response.isSuccessful) {
                    val deleteResponse = response.body()
                    callback(deleteResponse!!)
                } else {
                    Log.d("error", response.message())
                }
                }
            override fun onFailure(call: Call<DeleteResponseModel>, t: Throwable) {
                Log.d("error", t.message ?: "Unknown error")
            }
        })

    }



    // thanh toán
    fun checkout(userId: String, callback: (CheckoutResponseModel?) -> Unit) {
        iRetrofit.checkoutCart(userId).enqueue(object : Callback<CheckoutResponseModel> {
            override fun onResponse(
                call: Call<CheckoutResponseModel>,
                response: Response<CheckoutResponseModel>
            ) {
                if (response.isSuccessful) {
                    val checkoutResponse = response.body()
                    callback(checkoutResponse)
                } else {
                    Log.d("errorcheckout", response.message())
                    callback(null)
                }
        }
            override fun onFailure(call: Call<CheckoutResponseModel>, t: Throwable) {
                Log.d("errorcheckout", t.message ?: "Unknown error")
                callback(null)
            }
        })
    }


}
