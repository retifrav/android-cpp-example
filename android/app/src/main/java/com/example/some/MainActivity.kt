package com.example.some

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.some.ui.theme.Layout
import com.example.some.ui.theme.SomeTheme
import kotlin.reflect.KFunction1

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            SomeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    GrilsGallery(
                        isSystemInDarkTheme(),
                        Grils.grilsData,
                        this::whoHasTheBestBoobs
                    )
                }
            }
        }
    }

    companion object
    {
        init {
            // if you, like a normal person, defined CMAKE_DEBUG_POSTFIX (to `d` value),
            // then this thing here will fail to find the `libcpp-wrapperd.so` file,
            // so you'll need to:
            // - either undefine CMAKE_DEBUG_POSTFIX;
            // - or change the name here to a d-postfixed variant;
            // - or hardcode your wrapper library build to `-DCMAKE_BUILD_TYPE=Release`,
            // either in `build.gradle.kts` or in CMakeLists.txt
            System.loadLibrary("cpp-wrapper")
        }
    }

    private external fun wrapperMessage(): String
    private external fun doThingy(): String
    private external fun whoHasTheBestBoobs(
        grils: String // don't forget that the wrapper function should also have this argument
    ): String

    private fun doSomething()
    {
        Log.i("log", wrapperMessage());
        Log.i("log", doThingy());
    }
}

@Composable
fun MinimalDialog(
    msg: String,
    onDismissRequest: () -> Unit
)
{
// https://medium.com/make-apps-simple/text-scrolling-in-jetpack-compose-deabc7f67156#c55c
//    val textMeasurer = rememberTextMeasurer()
//    val density = LocalDensity.current
//    val textHeight = with(density) {
//        textMeasurer.measure(
//            text = msg,
//            maxLines = 2, // doesn't seem to work, it is always set to the height of 1 line
//            style = TextStyle(),
//        ).size.height.toDp()
//    }
    Dialog(onDismissRequest = { onDismissRequest() })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                //.padding(30.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Text(
                    text = "Best grils (criteria unknown)",
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(18.0F, TextUnitType.Sp),
                    modifier = Modifier.padding(top = 15.dp)
                )

                Spacer(modifier = Modifier.weight(1.0f))

                Text(
                    text = msg,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        //.height(textHeight) // doesn't work, unfortunately
                        .height(45.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically)
                        .verticalScroll(rememberScrollState())
                )

                Spacer(modifier = Modifier.weight(1.0f))

                TextButton(
                    onClick = { onDismissRequest() },
                    modifier = Modifier.padding(bottom = Layout.padding),
                ) { Text("Oh wow") }
            }

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

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Year:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = gril.year.toString())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) // is there a fucking stable alternative?
@Composable
fun GrilsGallery(
    darkTheme: Boolean,
    grils: List<Gril>,
    whoHasTheBestBoobs: KFunction1<String, String>
)
{
    val openMinimalDialog = remember { mutableStateOf(false) }

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
                    containerColor = (
                        if (darkTheme) MaterialTheme.colorScheme.surfaceVariant
                        else MaterialTheme.colorScheme.surfaceTint
                    )
                )
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                content = { Text("Who is the best gril?") },
                onClick = {
//                    Log.i(
//                        "log",
//                        whoHasTheBestBoobs(
//                            grilsDataToJsonString(grils)
//                        )
//                    );
                    openMinimalDialog.value = !openMinimalDialog.value;
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

    when {
        openMinimalDialog.value -> {
            MinimalDialog(
                whoHasTheBestBoobs(
                    grilsDataToJsonString(grils)
                ),
                onDismissRequest = { openMinimalDialog.value = false }
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GrilCardPreview() {
//    SomeTheme {
//        GrilCard(
//            Gril(
//                "Christina Hendricks",
//                1975,
//                R.drawable.christina_hendricks,
//                5
//            )
//        )
//    }
//}

// don't know how to pass KFunction1<String, String> here
//@Preview(showBackground = true)
//@Composable
//fun GrilsGalleryPreview() {
//    SomeTheme {
//        GrilsGallery(
//            isSystemInDarkTheme(),
//            Grils.grilsData,
//            { "some string, doesn't matter" } // needs to conform to KFunction1<String, String>
//        )
//    }
//}