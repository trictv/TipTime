package com.trictv.tiptime

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.trictv.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

        val formattedtip = NumberFormat.getCurrencyInstance().format(0)
        binding.tipResult.text = getString(R.string.tip_amount, formattedtip)
    }

    private fun calculateTip() {
        //entrada
        val stringInTextField = binding.costOfService.text.toString()

        val const: Double = stringInTextField.toDoubleOrNull() ?: return

        val selectedId: Int = binding.tipOption.checkedRadioButtonId

        val tipPercentage: Double = when (selectedId) {
            R.id.option_twendy_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        //processamento
        var tip: Double = const * tipPercentage
        val roundUp: Boolean = binding.roundUpSwitch.isChecked

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }


        //saida
        val formattedtip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedtip)
    }
}