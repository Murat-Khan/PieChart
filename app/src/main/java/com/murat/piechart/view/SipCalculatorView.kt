package com.murat.piechart.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.murat.piechart.R
import com.murat.piechart.presenter.SipCalculatorPresenter
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat
import java.util.*

class SipCalculatorView : AppCompatActivity(), SipCalculatorViewInterface {

    // material text views
    private lateinit var totalInvestedAmountMaterialTextView: MaterialTextView
    private lateinit var estimatedReturnsMaterialTextView: MaterialTextView
    private lateinit var totalAmountMaterialTextView: MaterialTextView

    // material text input edit texts and text input layout
    private lateinit var monthlyInvestmentAmountTextInputEditText: TextInputEditText
    private lateinit var expectedReturnRateTextInputEditText: TextInputEditText
    private lateinit var investmentTimePeriodTextInputEditText: TextInputEditText
    private lateinit var monthlyInvestmentAmountTextInputLayout: TextInputLayout
    private lateinit var expectedReturnRateTextInputLayout: TextInputLayout
    private lateinit var investmentTimePeriodTextInputLayout: TextInputLayout

    // buttons
    private lateinit var sipCalculateResultButton: MaterialButton

    // pie chart
    private lateinit var sipResultPieChart: PieChart

    // calculation result strings
    private lateinit var monthlyInvestmentAmount: String
    private lateinit var expectedReturnRate: String
    private lateinit var investmentTimePeriod: String

    // sliders
    private lateinit var monthlyInvestmentAmountSlider: Slider
    private lateinit var expectedReturnRateSlider: Slider
    private lateinit var investmentTimePeriodSlider: Slider
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sip_calculator_view)

        // initialise all the UI elements


        // material textViews
        totalInvestedAmountMaterialTextView = findViewById(R.id.totalInvestedAmountMaterialTextView)
        estimatedReturnsMaterialTextView = findViewById(R.id.estimatedReturnsMaterialTextView)
        totalAmountMaterialTextView = findViewById(R.id.totalAmountMaterialTextView)

        // material text input editTexts and text input layout
        monthlyInvestmentAmountTextInputEditText =
            findViewById(R.id.monthlyInvestmentAmountTextInputEditText)
        expectedReturnRateTextInputEditText =
            findViewById(R.id.expectedReturnRateTextInputEditText)
        investmentTimePeriodTextInputEditText =
            findViewById(R.id.investmentTimePeriodTextInputEditText)
        monthlyInvestmentAmountTextInputLayout =
            findViewById(R.id.monthlyInvestmentAmountTextInputLayout)
        expectedReturnRateTextInputLayout =
            findViewById(R.id.expectedReturnRateTextInputLayout)
        investmentTimePeriodTextInputLayout =
            findViewById(R.id.investmentTimePeriodTextInputLayout)

        // buttons
        sipCalculateResultButton = findViewById(R.id.sipCalculateResultButton)

        // pie chart
        sipResultPieChart = findViewById(R.id.sipResultPieChart)

        // sliders
        monthlyInvestmentAmountSlider = findViewById(R.id.monthlyInvestmentAmountSlider)
        expectedReturnRateSlider = findViewById(R.id.expectedReturnRateSlider)
        investmentTimePeriodSlider = findViewById(R.id.investmentTimePeriodSlider)


        // setting initial values to all the UI elements

        // TextInputEditTexts
        monthlyInvestmentAmountTextInputEditText.setText("5000")
        expectedReturnRateTextInputEditText.setText("14")
        investmentTimePeriodTextInputEditText.setText("10")

        // sliders
        monthlyInvestmentAmountSlider.value = 5000f
        expectedReturnRateSlider.value = 14f
        investmentTimePeriodSlider.value = 10f

        val sipPresenter = SipCalculatorPresenter(this)
        sipPresenter.forCalculation(
            "5000",
            "14",
            "10"
        )


        // handling all listeners for all the UI elements

        // handling sliders
        monthlyInvestmentAmountSlider.addOnChangeListener { slider, value, fromUser ->
            monthlyInvestmentAmountTextInputEditText.setText(value.toInt().toString())
        }
        expectedReturnRateSlider.addOnChangeListener { slider, value, fromUser ->
            expectedReturnRateTextInputEditText.setText(value.toInt().toString())
        }
        investmentTimePeriodSlider.addOnChangeListener { slider, value, fromUser ->
            investmentTimePeriodTextInputEditText.setText(value.toInt().toString())
        }

        // handling buttons
        sipCalculateResultButton.setOnClickListener {

            monthlyInvestmentAmount = monthlyInvestmentAmountTextInputEditText.text.toString()
            expectedReturnRate = expectedReturnRateTextInputEditText.text.toString()
            investmentTimePeriod = investmentTimePeriodTextInputEditText.text.toString()

            if (checkAllFields()) {
                sipPresenter.forCalculation(
                    monthlyInvestmentAmount,
                    expectedReturnRate,
                    investmentTimePeriod
                )
            }
        }
    }

    // update the text views in the
    // result section of the view
    // and also update the pie chart
    override fun onCalculationResult(
        totalInvestedAmount: String,
        estimatedReturns: String,
        totalValue: String
    ) {

        val currencyFormatter: NumberFormat =
            NumberFormat.getCurrencyInstance(Locale("en", "IN", "#"))

        val totalInvestedAmountFormatted: String =
            currencyFormatter.format(totalInvestedAmount.toLong())
        val estimatedReturnsFormatted: String = currencyFormatter.format(estimatedReturns.toLong())
        val totalValueFormatted: String = currencyFormatter.format(totalValue.toLong())

        totalInvestedAmountMaterialTextView.text = totalInvestedAmountFormatted
        estimatedReturnsMaterialTextView.text = estimatedReturnsFormatted
        totalAmountMaterialTextView.text = totalValueFormatted

        sipResultPieChart.clearAnimation()
        sipResultPieChart.clearChart()

        sipResultPieChart.addPieSlice(
            PieModel(
                "Invested Amount",
                totalInvestedAmount.toFloat(),
                ContextCompat.getColor(this, R.color.blue_200)
            )
        )

        sipResultPieChart.addPieSlice(
            PieModel(
                "Estimated Returns",
                totalValue.toFloat() - totalInvestedAmount.toFloat(),
                ContextCompat.getColor(this, R.color.green_200)
            )
        )

        sipResultPieChart.startAnimation()
    }

    // check whether all the text fields are filled or not
    private fun checkAllFields(): Boolean {
        if (monthlyInvestmentAmount.isEmpty()) {
            monthlyInvestmentAmountTextInputEditText.error = "Can't be empty"
            return false
        }

        if (expectedReturnRate.isEmpty()) {
            expectedReturnRateTextInputEditText.error = "Can't be empty"
            return false
        }

        if (investmentTimePeriod.isEmpty()) {
            investmentTimePeriodTextInputEditText.error = "Can't be empty"
            return false
        }

        return true
    }
}
