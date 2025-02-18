package fileio;

import java.util.List;

public final class Input {
    private int numberOfTurns;
    private List<MonthlyUpdates> monthlyUpdates;
    private InitialData initialData;

    public Input() {
    }

    public Input(final int numberOfTurns, final List<MonthlyUpdates> monthlyUpdates,
                 final InitialData initialData) {
        this.numberOfTurns = numberOfTurns;
        this.monthlyUpdates = monthlyUpdates;
        this.initialData = initialData;
    }

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public List<MonthlyUpdates> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdates> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

}

