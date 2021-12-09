package com.example.android.tipcalci

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIALTIPPERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var BILLVALUE: EditText
    private lateinit var seekBar: SeekBar
    private lateinit var tippercent: TextView
    private lateinit var tipvalue: TextView
    private lateinit var totalvalue: TextView
    private lateinit var tipdescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BILLVALUE = findViewById(R.id.BILLVALUE)
        seekBar = findViewById(R.id.seekBar)
        tippercent = findViewById(R.id.tippercent)
        tipvalue = findViewById(R.id.tipvalue)
        totalvalue = findViewById(R.id.totalvalue)
        tipdescription = findViewById(R.id.tipdescription)

        seekBar.progress = INITIALTIPPERCENT
        tippercent.text = "$INITIALTIPPERCENT%"
        updateTipDescription(INITIALTIPPERCENT)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                Log.i(TAG, "On Progress Change $progress")
                tippercent.text = "$progress%"
                computerTipandTotal()
                updateTipDescription(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        BILLVALUE.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "After Text Changed $p0")

            }

        })
    }

    private fun updateTipDescription(progress: Int) {
       var tipdescriptiondup= when(progress)
        {
           in 0..5->"POOR"
           in 6..10->"OK"
           in 11..15->"ACCEPTABLE"
           in 16..20->"GOOD"
           in 21..25->"GREAT"
           else -> "AMAZING"
        }
        tipdescription.text=tipdescriptiondup
        var color=ArgbEvaluator().evaluate(
            progress.toFloat()/seekBar.max,
            ContextCompat.getColor(this,R.color.worst_color),
            ContextCompat.getColor(this,R.color.best_color)
        )as Int
        tipdescription.setTextColor(color)
    }

    private fun computerTipandTotal() {
        if (BILLVALUE.text.isEmpty()) {
            tipvalue.text = ""
            totalvalue.text = ""
            return
        }
        var billvalue = BILLVALUE.text.toString().toDouble()
        var TIPPERCENT = seekBar.progress
        var tip = billvalue * TIPPERCENT / 100
        var total = billvalue + tip
        tipvalue.text = "%.2f".format(tip)
        totalvalue.text = "%.2f".format(total)
    }
}