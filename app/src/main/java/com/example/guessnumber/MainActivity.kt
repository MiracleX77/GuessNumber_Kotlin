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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import kotlin.random.Random
import androidx.compose.material3.Button as Button


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
    // จำนวนครั้งที่ทาย
    var count by remember { mutableStateOf(0) }
    var isCounted by remember { mutableStateOf(false) }

    Toolbar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        QuestionText()
        CountText(count = count)
        InputField(
        onValueChange = { userGuess = it },
        onClick = {
            result = checkResult(randomValue, userGuess.toInt())
            if ((result == "low" || result == "high") && !isCounted) {
                count += 1

            }
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
                randomValue = setGame()
                
            }
            if(result == "low"){
                isCounted = true
                HintText(res = R.string.hint_low,isVisible=isVisible)
                isCounted = false

                // print answer
            }
            if(result =="high"){
                isCounted = true
                HintText(res = R.string.hint_high, isVisible = isVisible)
                isCounted = false

            }
        }
        AgainButton(
            onClick = { randomValue = setGame()
            isVisible = false
            result = ""
                count = 0}
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

// Show score
@Composable
fun ScoreText(score: Int) {
    Text(
        text = stringResource(id = R.string.score, score),
        color = Color(0xFF070707),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 70.dp, end = 16.dp, bottom = 8.dp),
        textAlign = TextAlign.Center

    )
}

// Show count
@Composable
fun CountText(count: Int) {
    Text(
        text = stringResource(id = R.string.count, count),
        color = Color(0xFF070707),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, top = 10.dp, end = 10.dp, bottom = 4.dp),
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
        // ตกแต่ง TextField
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFF070707),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF070707),
            placeholderColor = Color(0xFF070707),
        ),
        // add round corner
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp)
    )
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 130.dp, top = 20.dp, end = 16.dp, bottom = 8.dp),
            
    ){
        Text(
            text = stringResource(id = R.string.sub),
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
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
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun AgainButton(onClick: () -> Unit) {
    Button(
        onClick =  onClick,
        modifier = Modifier
            .padding(start = 120.dp, top = 10.dp, end = 16.dp, bottom = 8.dp),
    ) {
        Text(
            text = stringResource(id = R.string.again),
        )
    }
    

}


fun setGame(): Int {
    return Random.nextInt(1, 1000)
}
fun checkResult(numberRandom : Int,useGuessInt: Int ) : String {
    var result = ""
    println(numberRandom)
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