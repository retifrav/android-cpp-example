package com.example.some

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.some.ui.theme.Layout
import com.example.some.ui.theme.SomeTheme

data class Gril(
    val name: String,
    val photoID: Int
)

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            SomeTheme {
                // a surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    GrilsGallery(Grils.grilsData, this::doThingy)
                }
            }
        }
    }

    private external fun doThingy(): String

    companion object
    {
        init {
            // why the fuck does it need to be a part of the file name, what's the point then
            // of declaring LOCAL_MODULE in Android.mk?
            // and by the way, if you'll need to load a Debug variant (libThingyd.so),
            // then the name here would need to be Thingyd
            System.loadLibrary("Thingy")
        }
    }
}

@Composable
fun GrilCard(gril: Gril)
{
    Row(modifier = Modifier.padding(vertical = Layout.padding)) {
        Image(
            painter = painterResource(gril.photoID),
            contentDescription = "Profile picture",
            modifier = Modifier
                // set image size to 40 dp
                .width(150.dp)
                // clip image in a shape
                //.clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(Layout.padding))

        Column {
            Text(text = "Name:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = gril.name)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // is there a fucking stable alternative?
@Composable
fun GrilsGallery(grils: List<Gril>, doSomething: () -> String) // can also be () -> Unit, if it's a function without return value
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Total number of grils: ${Grils.grilsData.count()}",
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(24.0F, TextUnitType.Sp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = White
                    )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                content = { Text("Do something") },
                onClick = {
                    Log.i("log", "ololo");
                    Log.i("log", doSomething());
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(all = Layout.padding))
            {
                Spacer(modifier = Modifier.height(padding.calculateTopPadding()))

                LazyColumn {
                    items(grils) { gril -> GrilCard(gril) }
                }
            }
        }
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GrilCardPreview() {
//    SomeTheme {
//        GrilCard(
//            Gril(
//                "Christina Hendricks",
//                R.drawable.christina_hendricks
//            )
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun GrilsGalleryPreview() {
    SomeTheme {
        // don't know how to pass doThingy() function here,
        // but apparently there is no need for that,
        // given that this is just a static preview
        GrilsGallery(Grils.grilsData, { "some string, doesn't matter" })
        //GrilsGallery(Grils.grilsData, { /* and here can be some function, which also doesn't matter */ })
    }
}