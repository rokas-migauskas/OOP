package loan;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class AnnuityLoan extends Loan {
    private float currentRepayment;
    private float startRepayment;

    private float startInterest;
    private float currentInterest;

    private float startMortgage;

    private float currentMortgage;
    public AnnuityLoan(float amount, int years, int months, float yearlyPercentage, String loanType) {
        super(amount, years, months, yearlyPercentage, loanType);
        this.amount = super.amount;
        this.duration = super.duration;
        this.yearlyPercentage = super.yearlyPercentage;
        startInterest = super.amount * yearlyPercentage/12;
        startRepayment = roundFloat(getMonthlyPayment() - startInterest);
        currentInterest = startInterest;
        currentRepayment = startRepayment;
        startMortgage = amount;
        currentMortgage = startMortgage;

    }


    @Override
    public float getMonthlyPayment() {
        float monthlyPercentage = yearlyPercentage / 12;
        float x = 1 + monthlyPercentage;
        float y = (float) Math.pow(x, -duration);
        return roundFloat(amount * monthlyPercentage / (1-y));
    }


    @Override
    public float getMortgage(float monthsElapsed) {
        float monthlyPayment = getMonthlyPayment();
        float monthlyPercentage = yearlyPercentage / 12;
        currentMortgage =roundFloat( amount - startRepayment);
        currentRepayment = startRepayment;
        currentInterest = startInterest;
        if(monthsElapsed != 0) {
            for(int i = 0; i < monthsElapsed; ++i) {
                currentInterest = currentMortgage * monthlyPercentage;
                currentRepayment = monthlyPayment - currentInterest;
                currentMortgage = currentMortgage - currentRepayment;

            }

        }

        roundFloat(currentMortgage);
        setCurrentInterest(currentMortgage, monthlyPercentage);
        roundFloat(currentInterest);
        setCurrentRepayment(currentInterest, monthlyPayment);
        roundFloat(currentRepayment);


        return currentMortgage;
    }


    public void setCurrentInterest(float currentInterest) {
        this.currentInterest = currentInterest;
    }
    public void setCurrentInterest(float mortgage, float monthlyPercentage) {
        this.currentInterest = mortgage * monthlyPercentage;
    }

    public void setCurrentRepayment(float currentRepayment) {
        this.currentRepayment = currentRepayment;
    }
    public void setCurrentRepayment(float interest, float monthlyPayment) {
        this.currentRepayment = monthlyPayment - interest;
        roundFloat(currentRepayment);
    }


    public float getCurrentRepayment() {
        return currentRepayment;
    }
    public static float roundFloat(float value) {
        value = value * 100 / 100;
        return value;
    }



    public float getCurrentInterest() {
        return currentInterest;
    }

    public float getCurrentMortgage() {
        return currentMortgage;
    }

    public void increaseInterest(float interestRate) {
        interestRate = interestRate/12/100;
        currentInterest = currentInterest * (1+interestRate);
    }
}