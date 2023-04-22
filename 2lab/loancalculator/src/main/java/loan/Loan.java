package loan;

public abstract class Loan {
    protected float amount;
    public int duration;
    protected float yearlyPercentage;
    public String loanType;

    public Loan() {
        this.amount = 0;
        this.duration = 0;
        this.yearlyPercentage = 0;
    }

    public Loan(float amount, int years, int months, float yearlyPercentage, String loanType) {
        this.amount = amount;
        this.duration = years*12 + months;
        this.yearlyPercentage = yearlyPercentage;
        this.loanType = loanType;
    }

    public int getDuration() {
        return duration;
    }

    public float getAmount() {
        return amount;
    }

    public float getYearlyPercentage() {
        return yearlyPercentage;
    }

    public abstract float getMonthlyPayment();

    public abstract float getMortgage(float monthsElapsed);

    public abstract float getCurrentInterest();
    public abstract float getCurrentRepayment();

    public abstract float getCurrentMortgage();

    public abstract void increaseInterest(float interestRate);





}