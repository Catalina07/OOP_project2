package fileio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public final class Distributor implements EntityInput, Comparable<Distributor>, Observer {
    private int id;
    private int contractLength;
    private int initialBudget;
    private int initialInfrastructureCost;
    private int initialProductionCost;
    private int contract;
    private int budget;
    private boolean isBankrupt;
    private int energyNeededKW;
    private String producerStrategy;
    private int activeConsumers;
    private List<Producer> producers = new ArrayList<>();
    private boolean seeChanges;

    public boolean getSeeChanges() {
        return seeChanges;
    }

    public void setSeeChanges(boolean seeChanges) {
        this.seeChanges = seeChanges;
    }

    public void setEnergyNeededKW(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

    private Map<Integer, ContractOutput> consumers;

    public Distributor() {
    }


    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getContract() {
        return contract;
    }

    public void setContract(final int contract) {
        this.contract = contract;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(final int initialBudget) {
        this.initialBudget = initialBudget;
    }

    public int getInitialInfrastructureCost() {
        return initialInfrastructureCost;
    }

    public void setInitialInfrastructureCost(final int initialInfrastructureCost) {
        this.initialInfrastructureCost = initialInfrastructureCost;
    }

    public int getInitialProductionCost() {
        return initialProductionCost;
    }

    public void setInitialProductionCost(final int initialProductionCost) {
        this.initialProductionCost = initialProductionCost;
    }

    public Map<Integer, ContractOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final Map<Integer, ContractOutput> consumers) {
        this.consumers = consumers;
    }

    public int getActiveConsumers() {
        return activeConsumers;
    }

    public void setActiveConsumers(final int activeConsumers) {
        this.activeConsumers = activeConsumers;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public int getEnergyNeededKW() {
        return energyNeededKW;
    }

    public void setEnergyNeeded(int energyNeededKW) {
        this.energyNeededKW = energyNeededKW;
    }

    public String getProducerStrategy() {
        return producerStrategy;
    }

    public void setProducerStrategy(String producerStrategy) {
        this.producerStrategy = producerStrategy;
    }

    @Override
    public int compareTo(final Distributor o) {
        return Integer.compare(this.contract, o.getContract());
    }

    @Override
    public void update(Observable o, Object arg) {
        setSeeChanges(true);
    }
}
