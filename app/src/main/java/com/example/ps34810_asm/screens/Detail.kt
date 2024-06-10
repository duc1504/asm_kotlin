package com.example.ps34810_asm.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ps34810_asm.R
import com.example.ps34810_asm.helpers.RetrofitAPI
import com.example.ps34810_asm.httpmodels.AddCartRequestModel
import com.example.ps34810_asm.httpmodels.ProductDetailResponseModel
import com.example.ps34810_asm.httpmodels.Productmodel
import com.example.ps34810_asm.ui.theme.BlackLitle
import com.example.ps34810_asm.ui.theme.Color3
import com.example.ps34810_asm.ui.theme.GrayLittle
import com.example.ps34810_asm.ui.theme.Ps34810_AsmTheme

class Detail {
    @Composable
    fun Container(value: String = "",goToScreen: (String) -> Unit) {
        Body(value= value,goToScreen=goToScreen)
    }

    @Composable
    fun Body(value: String,goToScreen: (String) -> Unit) {
        val context = LocalContext.current
        val sharePref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        var userId =sharePref.getString("userId", "") ?: "";

        var product by remember { mutableStateOf(Productmodel(null, null, null, null, null, null, null, null,)) }
        var quantity by remember { mutableStateOf(1) }
        val painter = rememberAsyncImagePainter(model = product.image);

        fun getProductsCallback(response : ProductDetailResponseModel?) {
                if (response != null) {
                    product = response.product

                }
        }

        fun getProductById() {
            try {
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.getProductDetail(id=value, ::getProductsCallback)
            }catch (e: Exception){

            }

        }
        getProductById()

        fun Addcart () {
        var body = AddCartRequestModel(userId=userId, productId=value,quantity=quantity)
            try {
                val retrofitAPI = RetrofitAPI()
                retrofitAPI.addProductToCart(body){
                    response ->
                    if (response?.status == true) {
                        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: Exception){
                Log.e("Addcart", e.toString())
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(start = 55.dp)
                            .clip(RoundedCornerShape(bottomStart = 20.dp))
                            .paint(
                                painter = painter,
                                contentScale = ContentScale.Crop,
                            )
                    ) {}

                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 20.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .width(55.dp)
                            .height(55.dp)
                            .clickable { goToScreen("home") },
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = null,
                            modifier = Modifier
                                .height(28.dp)
                                .width(28.dp)
                                .align(alignment = Alignment.Center),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 130.dp, start = 15.dp)
                            .background(Color.White, shape = RoundedCornerShape(30.dp))
                            .width(70.dp)
                            .height(200.dp),
                    ) {
                        Column(
                            Modifier
                                .align(alignment = Alignment.Center)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.color1),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.color2),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.color3),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                            )
                        }
                    }
                }
                Column(Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "${product.name}", color = Color.Black,
                        fontWeight = FontWeight(500),
                        fontSize = 24.sp, modifier = Modifier.padding(vertical = 20.dp)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Text(
                                text = "$ ${product.price}", color = Color.Black,
                                fontWeight = FontWeight(500),
                                fontSize = 30.sp
                            )
                        }
                        Row {
                            Image(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        quantity = (quantity - 1).coerceAtLeast(1)
                                    }

                            )
                            Text(
                                text = "${quantity}",
                                color = Color.Black,
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                                    .align(alignment = Alignment.CenterVertically),
                            )
                            Image(
                                painter = painterResource(id = R.drawable.plus),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        quantity = (quantity + 1).coerceAtMost(product.quantity ?: Int.MAX_VALUE)

                                    }
                            )
                        }
                    }
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.star),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = "4.6",
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(500),
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                        )
                        Text(
                            text = "(50 reviews)",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically),
                            color = Color3
                        )
                    }
                    Text(
                        text = "${product.description}",
                        color = Color3,
                        modifier = Modifier.padding(vertical = 10.dp),
                        letterSpacing = 1.sp,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.padding(20.dp)) {
                    Row {
                        Box() {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .background(GrayLittle, shape = RoundedCornerShape(10.dp))
                                        .width(60.dp)
                                        .height(60.dp),
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.bookmark),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .height(24.dp)
                                            .width(24.dp)
                                            .align(alignment = Alignment.Center),
                                    )
                                }

                                Button(
                                    onClick = {
                                        Addcart()
                                    },
                                    modifier = Modifier
                                        .padding(start = 10.dp)
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .clip(shape = RoundedCornerShape(10.dp)),
                                    shape = RoundedCornerShape(6.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = BlackLitle,
                                        contentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        text = "Add to cart",
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Ps34810_AsmTheme {
            Body(value= "2",goToScreen = {})
        }
    }
}
