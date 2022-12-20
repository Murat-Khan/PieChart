package com.murat.piechart.model

class SipCalculatorModel(
    monthlyInvestmentAmount: String,
    expectedReturnRate: String,
    investmentTimePeriod: String) : SipCalculatorModelInterface{

    val TAG = SipCalculatorModel::class.java.simpleName

    // convert all the inputs to integer.
    private var monthlyInvestmentAmountInt: Int = monthlyInvestmentAmount.toInt()
    private var expectedReturnRateInt: Int = expectedReturnRate.toInt()
    private var investmentTimePeriodInt: Int = investmentTimePeriod.toInt() * 12

    // total investment is considered here is according to monthly investment plans
    override fun getTotalInvestedAmount(): Long {
        return (monthlyInvestmentAmountInt * investmentTimePeriodInt).toLong()
    }

    // estimated returns = maturity value - total investment amount
    override fun getEstimatedReturns(): Long {
        return getTotalValue() - getTotalInvestedAmount()
    }

    // calculate the maturity value according to the formula
    override fun getTotalValue(): Long {
        val periodicInterest: Float = ((expectedReturnRateInt.toFloat() / 12) / 100)

        return (monthlyInvestmentAmountInt * (((Math.pow(
            (1 + periodicInterest).toDouble(),
            investmentTimePeriodInt.toDouble()
        )
                - 1) / periodicInterest) * (1 + periodicInterest)))
            .toLong()
    }





}