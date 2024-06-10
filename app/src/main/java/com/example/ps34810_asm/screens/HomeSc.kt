package com.example.ps34810_asm.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
//import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.ps34810_asm.R
import com.example.ps34810_asm.helpers.RetrofitAPI
import com.example.ps34810_asm.httpmodels.ProductResponseModel
import com.example.ps34810_asm.httpmodels.Productmodel
import com.example.ps34810_asm.ui.theme.Ps34810_AsmTheme
import com.fasterxml.jackson.annotation.JsonProperty

class HomeSc {

    @Composable
    fun Container(gotoSceen: (String) -> Unit) {
        Body(gotoSceen=gotoSceen)
    }

    @Composable
    fun Body(gotoSceen: (String) -> Unit) {
        var listproducts by remember { mutableStateOf(emptyList<Productmodel>()) }

        fun getProductCb(response: ProductResponseModel?) {
            val products = response?.data ?: emptyList()  // Gán danh sách rỗng nếu products là null
            listproducts = products
        }

        fun getProduct() {
            val retrofit = RetrofitAPI()
            retrofit.getProducts { response ->
                getProductCb(response)
            }
        }

        SideEffect {
            getProduct()
        }

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
                Column {
                    Text(
                        text = "Make home",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "BEAUTIFUL",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700)
                    )
                }
                Box(
                    modifier = Modifier.align(alignment = Alignment.CenterVertically)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.bi_cart2),
                        contentDescription = "cart",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .clickable { gotoSceen("cart") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(listproducts) { listproducts ->
                    ProductItem(listproducts,gotoSceen=gotoSceen)
                }
            }
        }
    }

    @Composable
    fun ProductItem(product: Productmodel, gotoSceen: (String) -> Unit) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image)
                .size(Size.ORIGINAL)
                .build()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .width(150.dp)
                .clickable { gotoSceen("detail/${product.id}") }
        ) {
            Box(
                modifier = Modifier
                    .height(250.dp)
                    .width(150.dp)
                    .padding(bottom = 8.dp)
            ) {
                // Hình ảnh sản phẩm lớn
                Image(
                    painter =painter,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Logo nhỏ nằm trên hình ảnh sản phẩm ở góc dưới bên phải
                Image(
                    painter = painterResource(id = R.drawable.bag),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .width(40.dp)
                        .height(40.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Column {
                Text(text = "${product.name}")
                Text(
                    text = "$${product.price}",
                    color = Color.Black,
                    fontWeight = FontWeight(700),
                    fontSize = 16.sp
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        Ps34810_AsmTheme {
            Body(gotoSceen = {})
        }
    }
}