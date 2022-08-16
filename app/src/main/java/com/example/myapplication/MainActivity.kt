package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {

    Column {
        ComposeButton()
        AndroidButton()
        AndroidCodeButton()
        SelectableIcon()
        SelectableImageView()

    }
}

@SuppressLint("ClickableViewAccessibility")
@Composable
private fun ColumnScope.AndroidCodeButton() {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.buttonplain_layout, null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        update = {
            it.findViewById<Button>(R.id.buttonplain_view).run {
                this@run.background = resources.getDrawable(R.drawable.button_unselected)
                setOnTouchListener { _, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            this@run.background =
                                resources.getDrawable(R.drawable.button_selected)
                            true
                        }
                        MotionEvent.ACTION_UP -> {
                            this@run.background =
                                resources.getDrawable(R.drawable.button_unselected)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun ColumnScope.AndroidButton() {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.button_layout, null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        update = {}
    )
}

@Composable
private fun ColumnScope.ComposeButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val color = if (isPressed) Color.Blue else Color.Green
        Button(
            onClick = {},
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(backgroundColor = color),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                "BUTTON",
                color = Color.White
            )
        }
    }
}

@Composable
private fun ColumnScope.SelectableImageView() {
    AndroidView(
        factory = {
            View.inflate(it, R.layout.image_layout, null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        update = {
            it.findViewById<ImageView>(R.id.image_view).run {
                setOnClickListener {
                    this.isSelected = !this.isSelected
                }
            }

        }
    )
}

@Composable
private fun ColumnScope.SelectableIcon() {
    var selected by remember {
        mutableStateOf(true)
    }

    val drawableResource = if (
        selected) R.drawable.ic_baseline_back_hand_24
    else R.drawable.ic_outline_back_hand_24

    Icon(
        painter = painterResource(id = drawableResource),
        contentDescription = "contentDescription",
        modifier = Modifier
            .clickable { selected = !selected }
            .fillMaxWidth()
            .weight(1f)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting()
    }
}