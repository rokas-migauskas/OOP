package gui;

public class TableData {
    private int index;
    private float monthlySum;
    private float interest;
    private float repayment;
    private float mortgage;

    public TableData(int index, float monthlySum, float interest, float repayment, float mortgage) {
        this.index = index;
        this.monthlySum = monthlySum;
        this.interest = interest;
        this.repayment = repayment;
        this.mortgage = mortgage;
    }

    public int getIndex() {
        return index;
    }

    public float getMonthlySum() {
        return monthlySum;
    }

    public float getInterest() {
        return interest;
    }

    public float getRepayment() {
        return repayment;
    }

    public float getMortgage() {
        return mortgage;
    }
}
