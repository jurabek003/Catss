@file:OptIn(ExperimentalCoilApi::class)

package uz.turgunboyevjurabek.catss

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import uz.turgunboyevjurabek.catss.madels.GetImageResponse
import uz.turgunboyevjurabek.catss.madels.GetImageResponseItem
import uz.turgunboyevjurabek.catss.ui.theme.CatssTheme
import uz.turgunboyevjurabek.catss.utils.Status
import uz.turgunboyevjurabek.catss.vm.ImagesViewModel

class MainActivity : ComponentActivity() {
    private lateinit var imagesViewModel: ImagesViewModel
    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatssTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val scope= rememberCoroutineScope()
                    var data:GetImageResponse by remember { mutableStateOf(GetImageResponse()) }
                    val context= LocalContext.current
                    LaunchedEffect(key1 = true){
                        imagesViewModel=ViewModelProvider(this@MainActivity)[ImagesViewModel::class.java]
                        imagesViewModel.getCats(20).observe(this@MainActivity, Observer {
                            when(it.status){
                                Status.LOADING -> {
                                    Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                                }
                                Status.ERROR -> {
                                    Toast.makeText(context, "afsus ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                                Status.SUCCESS -> {
                                    data= it.data!!
                                }
                            }
                        })
                    }//finish Launched

                    Column(modifier = Modifier.fillMaxSize()) {
                        List(list = data)
                    }//finish Column
                }
            }
        }
    }

    @Composable
    fun List(list:GetImageResponse) {
        LazyColumn{
            items(list.size){
                UI(getImageResponseItem = list[it])
            }
        }
    }

    @Composable
    fun UI(getImageResponseItem: GetImageResponseItem) {
        val height=getImageResponseItem.height
        val with=getImageResponseItem.width
        ElevatedCard(
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(height.dp)
                .width(with.dp)
        ) {
            Image(painter = rememberImagePainter(data = getImageResponseItem.url,
                builder = {
                    crossfade(true)
                }),
                contentDescription =null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }

    }

//    @Preview(showSystemUi = true)
//    @Composable
//    fun showUI() {
//
//        UI()
//
//    }
}
