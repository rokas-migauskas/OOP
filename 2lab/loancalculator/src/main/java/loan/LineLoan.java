package loan;

public class LineLoan extends Loan {

    private float currentInterest;
    private float currentRepayment;

    private float startInterest;
    private float currentMortgage;

    public LineLoan(float amount, int years, int months, float yearlyPercentage, String loanType) {
        super(amount, years, months, yearlyPercentage, loanType);
        this.amount = amount;
        this.duration = duration;
        this.yearlyPercentage = yearlyPercentage;
        this.loanType = loanType;
        currentMortgage = amount;
        setCurrentInterest(currentMortgage);
        setCurrentRepayment(currentInterest);
    }

    @Override
    public float getMonthlyPayment() {
        return AnnuityLoan.roundFloat(amount / duration);
    }


    @Override
    public float getMortgage(float monthsElapsed) {
        monthsElapsed++;
        float monthlyPayment = getMonthlyPayment();
        currentMortgage = AnnuityLoan.roundFloat(amount - monthlyPayment * monthsElapsed);
        setCurrentInterest(currentMortgage);
        setCurrentRepayment(currentInterest);
        return currentMortgage;
    }
    public float getCurrentRepayment() {
        return currentRepayment;
    }

    public float getCurrentInterest() {
        return currentInterest;
    }
    public void setCurrentInterest(float currentMortgage) {
        currentInterest = AnnuityLoan.roundFloat(currentMortgage * yearlyPercentage/12);
    }
    public void setCurrentRepayment(float currentInterest) {
        currentRepayment = AnnuityLoan.roundFloat(getMonthlyPayment() + getCurrentInterest());
    }

    @Override
    public float getCurrentMortgage() {
        return currentMortgage;
    }
    public void increaseInterest(float interestRate) {
        interestRate = interestRate/12/100;
        currentInterest = currentInterest * (1+interestRate);
    }
}