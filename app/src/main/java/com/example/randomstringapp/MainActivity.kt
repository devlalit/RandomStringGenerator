package com.example.randomstringapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RandomStringScreen()
            }
        }
    }
}
@Composable
fun RandomStringItemView(item: RandomStringItem, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Generated String: ${item.value}")
            Spacer(modifier = Modifier.height(3.dp))
            Text("Length: ${item.length}")
            Spacer(modifier = Modifier.height(3.dp))
            Text("Timestamp: ${item.timestamp}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Delete", color = Color.Red, modifier = Modifier.clickable { onDelete() })
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RandomStringScreen(viewModel: RandomStringViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var length by remember { mutableStateOf("") }
    val context = LocalContext.current  // Get context for Toast
    val randomStrings by viewModel.randomStrings.observeAsState(emptyList())

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = length.toString(),
            onValueChange = { length = (it.toIntOrNull() ?: "").toString() },
            label = { Text(text = "Enter String Length",  fontWeight = FontWeight.Bold) },
            modifier = Modifier.fillMaxWidth()
        )


        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (length.isNotEmpty() && length.toInt() > 0) {
                viewModel.fetchRandomString(length) }else{
                Toast.makeText(context, "Please enter a valid length", Toast.LENGTH_SHORT).show()
                }
        }) {
            Text("Generate Random String")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if (randomStrings.size>0) {
            viewModel.deleteAllStrings()
            }else{
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Delete All Strings")
        }
        LazyColumn {
            items(randomStrings) { item ->
                RandomStringItemView(item, onDelete = { viewModel.deleteString(item) })
            }
        }
    }
}