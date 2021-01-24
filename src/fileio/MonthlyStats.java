package fileio;

import java.util.List;

public final class MonthlyStats implements EntityOutput {
    private int month;
    private List<Integer> distributorsIds;

    public MonthlyStats() { }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Integer> getDistributorsIds() {
        return distributorsIds;
    }

    public void setDistributorsIds(List<Integer> distributorsIds) {
        this.distributorsIds = distributorsIds;
    }
}
