package fileio;

public final class Consumer implements EntityInput {

    private int id;
    private int initialBudget;
    private int monthlyIncome;
    private int budget;
    private int sw;
    private boolean isBankrupt;
    private int debt;
    private Distributor debtToDistributor;

    public Consumer() { }

    public Consumer(final int id, final int initialBudget, final int monthlyIncome) {
        this.id = id;
        this.initialBudget = initialBudget;
        this.monthlyIncome = monthlyIncome;
        this.budget = 0;
        this.sw = 0;
        this.isBankrupt = false;
        this.debt = 0;
        this.debtToDistributor = new Distributor();
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(final int sw) {
        this.sw = sw;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public int getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(final int monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(final int debt) {
        this.debt = debt;
    }

    public Distributor getDebtToDistributor() {
        return debtToDistributor;
    }

    public void setDebtToDistributor(final Distributor debtToDistributor) {
        this.debtToDistributor = debtToDistributor;
    }

}
