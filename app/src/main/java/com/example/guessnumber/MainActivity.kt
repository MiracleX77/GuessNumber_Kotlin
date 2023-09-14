package com.example.guessnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.guessnumber.ui.theme.GuessnumberTheme
import kotlin.math.absoluteValue
import kotlin.random.Random


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessnumberTheme {
                MainScreen()
            }
        }
    }
}

@Preview
@Composable
fun MainScreen() {
    var randomValue by remember { mutableStateOf(setGame()) }
    var userGuess by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(true) }

    Toolbar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        QuestionText()
        InputField(
            onValueChange = {userGuess = it},
            onClick={
                result = checkResult(randomValue,userGuess.toInt())
            },
            randomValue = randomValue,
            userGuess = userGuess,
            result = result)
        if(result!=""){
            if(!isVisible){
                isVisible = true
            }
            if(result == "True"){
                HintText(res = R.string.hint_correct,isVisible=isVisible)
            }
            if(result == "low"){
                HintText(res = R.string.hint_low,isVisible=isVisible)
            }
            if(result =="high"){
                HintText(res = R.string.hint_high, isVisible = isVisible)
            }
        }
        AgainButton(
            onClick = { randomValue = setGame()
            isVisible = false
            result = ""}

        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = Color.White,
                        fontSize = 30.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()

            )
 }


@Composable
fun QuestionText() {
    Text(
        text = stringResource(id = R.string.text_qes),
        color = Color(0xFF070707),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 70.dp, end = 16.dp, bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    randomValue: Int,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,

    userGuess: String,
    result: String
) {
    TextField(
        value = userGuess,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(text = stringResource(id = R.string.input))
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 200.dp)
    )
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 120.dp, top = 10.dp, end = 16.dp, bottom = 8.dp),
    ){
        Text(
            text = stringResource(id = R.string.sub),
            color = Color.Blue
        )
    }


}

@Composable
fun HintText(res : Int,isVisible : Boolean) {
    if (isVisible) {
        Text(
            text = stringResource(id = res),
            color = Color(0xFF070707),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AgainButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick =  onClick,
        modifier = Modifier
            .padding(start = 120.dp, top = 10.dp, end = 16.dp, bottom = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.again),
            color = Color.Blue
        )
    }
}


fun setGame(): Int {
    return Random.nextInt(1, 1000)
}
fun checkResult(numberRandom : Int,useGuessInt: Int ) : String {
    var result = ""
    if(numberRandom == useGuessInt){
        result = "True"
    }
    else if (numberRandom > useGuessInt){
        result = "low"
    }
    else{
        result = "high"
    }
    return result
}