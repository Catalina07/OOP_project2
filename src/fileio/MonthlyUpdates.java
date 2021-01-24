package fileio;

import java.util.List;

public final class MonthlyUpdates {
    private List<NewConsumers> newConsumers;
    private List<DistributorChanges> distributorChanges;
    private List<ProducerChanges> producerChanges;

    public MonthlyUpdates() {
    }

    public MonthlyUpdates(final List<NewConsumers> newConsumers,
                          final List<DistributorChanges> distributorChanges,
                          final List<ProducerChanges> producerChanges) {
        this.newConsumers = newConsumers;
        this.distributorChanges = distributorChanges;
        this.producerChanges = producerChanges;
    }

    public List<NewConsumers> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(final List<NewConsumers> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<DistributorChanges> getDistributorChanges() {
        return distributorChanges;
    }

    public void setDistributorChanges(final List<DistributorChanges> distributorChanges) {
        this.distributorChanges = distributorChanges;
    }

    public List<ProducerChanges> getProducerChanges() {
        return producerChanges;
    }

    public void setProducerChanges(List<ProducerChanges> producerChanges) {
        this.producerChanges = producerChanges;
    }
}
