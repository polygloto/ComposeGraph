package com.mikhailovalx.composegraph

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikhailovalx.composegraph.ui.theme.ComposeGraphTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGraphTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GraphView(contract = mockedContract)
                }
            }
        }
    }
}

val mockedContract = GraphContract(
    verticalItems = listOf(
        GraphDot("0", 0),
        GraphDot("50000", 1),
        GraphDot("100000", 2),
        GraphDot("150000", 3),
        GraphDot("200000", 4),
    ),
    horizontalItems = listOf(),
    verticalPadding = PaddingValues(4.dp)
)

data class GraphContract(
    val verticalItems: List<GraphDot>,
    val horizontalItems: List<GraphDot>,
    val verticalPadding: PaddingValues
)

data class GraphDot(
    val title: String,
    val index: Int,
)

@Composable
fun GraphView(
    modifier: Modifier = Modifier,
    contract: GraphContract,
) {
    MeasureComposableHeight(
        viewToMeasure = {
            VerticalItem(
                text = "Mocked",
                padding = contract.verticalPadding
            )
        }
    ) { verticalItemHeight ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Row(modifier = modifier.height(IntrinsicSize.Min)) {
                Column(modifier = Modifier.wrapContentSize()) {
                    contract.verticalItems.sortedByDescending {
                        it.index
                    }.forEach { verticalItem ->
                        VerticalItem(
                            text = verticalItem.title,
                            padding = contract.verticalPadding
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(
                            top = contract.verticalPadding.calculateTopPadding(),
                            bottom = verticalItemHeight / 2
                        ),
                    color = Color(0xFFB3B3B3),
                )
            }
        }
    }
}

@Composable
fun VerticalItem(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 12,
    padding: PaddingValues = PaddingValues(4.dp)
) {
    Text(
        modifier = modifier
            .wrapContentSize()
            .padding(padding),
        text = text,
        fontSize = fontSize.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeGraphTheme {
        GraphView(contract = mockedContract)
    }
}