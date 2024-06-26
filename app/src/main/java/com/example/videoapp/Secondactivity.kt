package com.example.videoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.videoapp.ui.theme.VideoappTheme
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Secondactivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val Bhagvanji = intent.getStringExtra("Bhagvanji")
        enableEdgeToEdge()
        setContent {
            VideoappTheme {
                if (Bhagvanji != null) {
                    Greeting2(Bhagvanji)
                }
            }
        }
    }
}

data class VideoData(val title: String, val thumbnailUrl: String, val videoUrl: String)

@Composable
fun Greeting2(Bhagvanji:String) {
    val context = LocalContext.current
    val vlist = remember { mutableStateListOf<VideoData>() }
    val database = FirebaseDatabase.getInstance().reference
    val ref = database.child(Bhagvanji)


    ref.addListenerForSingleValueEvent(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
                for (VSnapshot in snapshot.children){
                    Log.d("title","$VSnapshot" )
                  val VTitle = VSnapshot.child("Title").getValue(String::class.java)
                    val VThumbnailUrl = VSnapshot.child("Thumbnailurl").getValue(String::class.java)
                    val VVideoUrl = VSnapshot.child("vurl").getValue(String::class.java)

                    Log.d("title2","$VTitle" )
                    if (VTitle != null && VThumbnailUrl != null && VVideoUrl != null) {
                        vlist.add(VideoData(VTitle, VThumbnailUrl, VVideoUrl))
                    }
                }
            Log.d("purilist","$vlist")
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,"couldnt find categories ", Toast.LENGTH_LONG).show()
        }
    })

    LazyColumn {
items(vlist.size){
    Index -> Layout2(Vtitle = vlist[Index])
}
    }
}
@Composable
fun Layout2(Vtitle:VideoData){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(10.dp)
            .background(Color.DarkGray)
            .clickable {
                val intent = Intent(context, Thirdactivity::class.java)
                intent.putExtra("vurl",Vtitle.thumbnailUrl)
                context.startActivity(intent)
            }
            .fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(data = Vtitle.thumbnailUrl),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = Vtitle.title,
            modifier = Modifier.padding(10.dp)
        )
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    VideoappTheme {
//        Greeting2("Android")
//    }
//}