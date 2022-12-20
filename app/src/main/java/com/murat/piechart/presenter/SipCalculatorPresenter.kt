package com.murat.piechart.presenter

import com.murat.piechart.model.SipCalculatorModel
import com.murat.piechart.view.SipCalculatorViewInterface

class SipCalculatorPresenter(
    private val sipCalculatorViewInterface: SipCalculatorViewInterface):SipCalculatorPresenterInterface {
    override fun forCalculation(
        monthlyInvestmentAmount: String,
        expectedReturnRate: String,
        investmentTimePeriod: String
    ) {

        // create instance of the sip model and calculate all the results.
        val sipModel = SipCalculatorModel(
            monthlyInvestmentAmount,
            expectedReturnRate,
            investmentTimePeriod
        )

        // pass the data to view by accepting the context of the view class
        sipCalculatorViewInterface.onCalculationResult(
            sipModel.getTotalInvestedAmount().toString(),
            sipModel.getEstimatedReturns().toString(),
            sipModel.getTotalValue().toString()
        )
    }

}