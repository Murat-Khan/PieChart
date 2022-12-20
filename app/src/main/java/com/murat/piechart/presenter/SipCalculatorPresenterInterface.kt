package com.murat.piechart.presenter

interface SipCalculatorPresenterInterface {

    fun forCalculation(
        monthlyInvestmentAmount: String,
        expectedReturnRate: String,
        investmentTimePeriod: String
    )
}