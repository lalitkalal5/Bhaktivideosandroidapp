package com.example.videoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.videoapp.ui.theme.VideoappTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        enableEdgeToEdge()
        setContent {
            VideoappTheme {
                Greeting()
            }
        }
    }
}



@Composable
fun Greeting() {
    val context = LocalContext.current
//    val Categories = remember{ mutableListOf<String>() }
    val Categories = remember { mutableStateListOf<String>() }
    val database = FirebaseDatabase.getInstance().reference
//    val ref = database.child("Mahakalima").child("v1").child("vurl")

    database.addListenerForSingleValueEvent(object : ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            for(BhagvanSnapshot in snapshot.children){
                val bhagvanji = BhagvanSnapshot.key.toString()
                Log.d("none","$bhagvanji")
                bhagvanji.let {
                    Categories.add(it)
                }
            }
            Log.d("Purilist1","$Categories")
        }
        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context,"couldnt find categories ",Toast.LENGTH_LONG).show()
        }
    })
    LazyColumn {
        items(Categories.size){
            index -> Layout(Category = Categories[index].toString())
        }
    }

}

@Composable
fun Layout(Category : String){
    val context = LocalContext.current

        Card(
            modifier = Modifier
                .padding(10.dp)
                .background(Color.DarkGray)
                .fillMaxSize()
                .clickable {
                    val intent = Intent(context,Secondactivity::class.java)
                        intent.putExtra("Bhagvanji","$Category")
                    context.startActivity(intent)
                }

        ) {Text(
            text = " Bhagvan $Category",
            modifier = Modifier.padding(10.dp)
        )}
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    VideoappTheme {
//        Greeting("Android")
//    }
//}