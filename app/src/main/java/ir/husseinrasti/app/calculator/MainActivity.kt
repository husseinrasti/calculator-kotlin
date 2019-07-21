package ir.husseinrasti.app.calculator

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var lastNumber : Double = 0.0
    private var lastOperator : String = ""
    private var mustClearLcd : Boolean = false
    private var mustOverrideOperator : Boolean = false


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val font = Typeface.createFromAsset(applicationContext.assets , "fonts/lcd.ttf")

        miniText.typeface = font
        resultText.typeface = font
        for (i in 0..9) {
            getButton("$i").typeface = font
        }
        btnPlus.typeface = font
        btnMinus.typeface = font
        btnMultiply.typeface = font
        btnDevide.typeface = font
        btnPoint.typeface = font
        btnEqual.typeface = font
        btnClear.typeface = font
        btnClearEntery.typeface = font
    }

    fun onClickNumber(view : View) {
        view as Button
        val number = view.text.toString()
        val oldNumber = resultText.text.toString()

        mustOverrideOperator = false

        if (mustClearLcd) {
            resultText.text = number
            mustClearLcd = false
            return
        }

        if (number.contains(".")) {
            if (!(oldNumber.contains("."))) {
                resultText.text = "$oldNumber$number"
                return
            }
            return
        }

        if (oldNumber == "0") {
            resultText.text = number
        } else if (oldNumber.length < 10) {
            resultText.text = "$oldNumber$number"
        }

    }

    fun onClickOperator(view : View) {
        view as Button
        val operator = view.text.toString()
        val number = resultText.text.toString().toDouble()

        if (operator.contains("C")) {
            mustOverrideOperator = false
            mustClearLcd = false
            lastOperator = ""
            lastNumber = 0.0
            miniText.text = ""
            resultText.text = "0"
            return
        }

        mustClearLcd = true

        if (mustOverrideOperator) {
            lastOperator = operator
            val oldMiniText = miniText.text.toString()
            val croppedNumber = oldMiniText.substring(0 , oldMiniText.length - 2)
            miniText.text = "$croppedNumber $operator"
            return
        }

        if (lastOperator == "=") {
            miniText.text = "$number $operator"
        } else {
            val oldMiniText = miniText.text.toString()
            miniText.text = "$oldMiniText $number $operator"
        }

        mustOverrideOperator = true

        if (lastNumber == 0.0) {
            lastNumber = number
        } else {
            var result = 0.0
            when (lastOperator) {
                "+" -> result = lastNumber + number
                "-" -> result = lastNumber - number
                "*" -> result = lastNumber * number
                "/" -> result = lastNumber / number
                "=" -> result = number
            }
            lastNumber = result
            resultText.text = "$result"
        }

        lastOperator = operator
    }

    private fun getButton(str : String?) : Button {
        return when (str) {
            "1" -> btn1
            "2" -> btn2
            "3" -> btn3
            "4" -> btn4
            "5" -> btn5
            "6" -> btn6
            "7" -> btn7
            "8" -> btn8
            "9" -> btn9
            "0" -> btn0
            "+" -> btnPlus
            "*" -> btnMultiply
            "/" -> btnDevide
            "-" -> btnMinus
            "C" -> btnClear
            "CE" -> btnClearEntery
            "=" -> btnEqual
            "." -> btnPoint
            else -> {
                btn0
            }
        }
    }
}
