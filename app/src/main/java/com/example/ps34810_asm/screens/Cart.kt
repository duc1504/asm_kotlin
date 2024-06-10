package com.example.ps34810_asm.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.ps34810_asm.R
import com.example.ps34810_asm.helpers.RetrofitAPI
import com.example.ps34810_asm.httpmodels.CartItem
import com.example.ps34810_asm.httpmodels.CartResponseModel
import com.example.ps34810_asm.httpmodels.UpdateCartRequestModel
import com.example.ps34810_asm.httpmodels.UpdateCartResponseModel
import com.example.ps34810_asm.ui.theme.BlackLitle
import com.example.ps34810_asm.ui.theme.GrayLittle
import com.example.ps34810_asm.ui.theme.Ps34810_AsmTheme

class Cart {
    @Composable
    fun Container() {
        Body()
    }

    @Composable
    fun Body() {
        val context = LocalContext.current
        val sharePref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val userId = sharePref.getString("userId", "") ?: ""

        Log.d("userId", userId)

        var carts by remember { mutableStateOf<List<CartItem>?>(null) }

        var totalPrice by rememberSaveable { mutableStateOf(0.0) }
        fun getCartCallback(response: CartResponseModel?) {
            if (response != null) {
                carts = response.cart.items
                totalPrice = response.cart.totalAmount?:0.0
                Log.d("Cart", carts.toString())
            }
        }

        fun getCart() {
            try {
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.getCartDetail(id = userId, ::getCartCallback)
            } catch (e: Exception) {
                // Handle exception
            }
        }

        // Update số lượng giỏ hàng
        fun updateCartCallback(response: UpdateCartResponseModel?) {
            if (response != null) {
                Log.d("updateCart", response.toString())
                getCart()
            }
        }

        fun updateCart(productId: String, quantity: Int) {

            try {
                val body = UpdateCartRequestModel(quantity = quantity)
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.updateCartItemQuantity(
                    userId = userId,
                    productId,
                    body,
                    ::updateCartCallback
                )
            } catch (
                e: Exception
            ) {
                Log.d("updateCart", e.toString())
            }
            getCart()
        }


        // xóa chi tiết giỏ hàng
        fun deleteCart(productId: String) {
            val retrofitAPI = RetrofitAPI()
            retrofitAPI.deleteCartItem(userId, productId){
                response ->

                if (response != null) {
                   if (response.status) {
                       Toast.makeText(context, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show()
                       getCart()
                   }
                   else
                       Toast.makeText(context, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                   }

                }

            }



         fun checkOut() {
             try {
                 val retrofitAPI = RetrofitAPI()
                 retrofitAPI.checkout(userId){
                     response ->
                     if (response != null) {
                         Log.d("checkOut", response.toString())
                       if (response.status) {
                           Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                       }
                         else
                             Toast.makeText(context, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show()
                     }
                 }
             } catch (e: Exception) {
                 Log.d("checkOut", e.toString())
         }
             getCart()
         }

//        updateCart("6661cea5c612e006f813d08a",0)


        // Gọi hàm để lấy thông tin giỏ hàng khi composable được khởi tạo
        // (sử dụng key hoặc LaunchedEffect để đảm bảo chỉ gọi một lần)

            getCart()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.search),
                        contentDescription = "search",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                }

                Text(
                    text = "My cart",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )

                Box {}
            }
            Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {

                        carts?.let { cartItems ->
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(cartItems.size) { index ->
                                    val cartItem = cartItems[index]
                                    val product = cartItem.product
                                    product?.let {
                                        cartItem.product?.let { product ->
                                            CartItem(cartItem = cartItem
                                                , updateCart = { productId, quantity -> updateCart(productId, quantity) }
                                                ,deleteCart={productId -> deleteCart(productId)})
                                        }
                                        Divider(
                                            color = GrayLittle,
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(vertical = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Hiển thị tổng giá trị đơn hàng
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    TotalPrice(totalPrice= totalPrice, checkOut = { checkOut() })
                }
            }

        }
    }

    @Composable
    fun CartItem(cartItem: CartItem, updateCart: (String, Int) -> Unit, deleteCart: (String) -> Unit) {
        val product = cartItem.product
        var quantity by remember { mutableStateOf(cartItem.quantity ?:5) }
        Row(modifier = Modifier.padding(bottom = 10.dp)) {
            // Hiển thị hình ảnh sản phẩm
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                product?.image?.let { imageUrl ->
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = "Product image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            // Hiển thị thông tin sản phẩm và giá
            Column(modifier = Modifier.padding(start = 10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${product?.name}",
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(20.dp)
                            .clickable { deleteCart(product?.id ?: "") }
                    )
                }
                Text(
                    text = "$ ${product?.price}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600)
                )

                Row(modifier = Modifier.padding(top = 12.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                            .clickable {

                                if (quantity > 1) {
                                    quantity -= 1
                                    updateCart(product?.id ?: "", quantity)
                                } else {
                                    // Xóa sản phẩm khỏi giỏ hàng khi số lượng là 0
                                    updateCart(product?.id ?: "", 0)
                                }

                            }
                    )
                    Text(
                        text = "${quantity}",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier
                            .height(28.dp)
                            .width(28.dp)
                            .clickable {
                                quantity += 1
                                updateCart(product?.id ?: "", quantity)

                            }
                    )
                }
            }
        }
    }

    @Composable
    fun TotalPrice(totalPrice: Double,checkOut: () -> Unit) {
        Box() {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier
                            .height(40.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                            .background(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                    Box(
                        modifier = Modifier
                            .background(BlackLitle, shape = RoundedCornerShape(10.dp))
                            .width(44.dp)
                            .height(44.dp)
                            .align(alignment = Alignment.CenterVertically),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.right),
                            contentDescription = null,
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp)
                                .align(alignment = Alignment.Center),

                            )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "$ ${totalPrice}",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Button(
                    onClick = {
                    checkOut()
                    },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlackLitle,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Check out",
                        fontSize = 20.sp
                    )
                }

            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Ps34810_AsmTheme {
            Body()
        }
    }
}
