package com.example.videoapp

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
//import androidx.media3.session.MediaController
import com.example.videoapp.ui.theme.VideoappTheme
import android.widget.MediaController

class Thirdactivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vurl = intent.getStringExtra("vurl")
            VideoappTheme {
               Greeting3(vurl)
            }
        }
    }
}

@Composable
fun Greeting3(vurl: String?) {
    AndroidView(factory = { context ->
        VideoView(context).apply {
            val uri = Uri.parse(vurl)
            setVideoURI(uri)
            setMediaController(MediaController(context).apply {
                setAnchorView(this@apply)
            })
            requestFocus()
            start()
        }
    })
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    VideoappTheme {
//        Greeting3("Android")
//    }
//}