package com.murat.piechart.view

interface SipCalculatorViewInterface {
    fun onCalculationResult(
        totalInvestedAmount: String,
        estimatedReturns: String,
        totalValue: String
    )
}