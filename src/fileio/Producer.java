package fileio;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public final class Producer extends Observable implements EntityInput {
    private int id;
    private int maxDistributors;
    private float priceKW;
    private String energyType;
    private int energyPerDistributor;
    private List<MonthlyStats> monthlyStats = new ArrayList<>();
    private List<Distributor> distributors = new ArrayList<>();

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<Distributor> distributors) {
        this.distributors = distributors;
    }

    public Producer() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnergyType() {
        return energyType;
    }

    public void setEnergyType(String energyType) {
        this.energyType = energyType;
    }

    public int getMaxDistributors() {
        return maxDistributors;
    }

    public void setMaxDistributors(int maxDistributors) {
        this.maxDistributors = maxDistributors;
    }

    public float getPriceKW() {
        return priceKW;
    }

    public void setPriceKW(float priceKW) {
        this.priceKW = priceKW;
    }

    public int getEnergyPerDistributor() {
        return energyPerDistributor;
    }

    public void setEnergyPerDistributor(int energyPerDistributor) {
        this.energyPerDistributor = energyPerDistributor;
    }

    /**
     *
     * @return decide what type of energy we have
     */
    public boolean decideEnergy() {
        if (this.energyType.equals("SOLAR") || this.energyType.equals("WIND")
                || this.energyType.equals("HYDRO")) {
            return true;
        }
        return false;
    }

    public List<MonthlyStats> getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(List<MonthlyStats> monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    /**
     *
     * @param energyPerDistributor method for Observer Design Pattern that involves different energy
     */
    public void setChanges(int energyPerDistributor) {
        setEnergyPerDistributor(energyPerDistributor);
        setChanged();
        notifyObservers();
    }
}
