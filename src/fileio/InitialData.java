package fileio;

import java.util.List;

public final class InitialData {
    private List<Consumer> consumers;
    private List<Distributor> distributors;
    private List<Producer> producers;

    public InitialData() {
    }

    public InitialData(final List<Consumer> consumers, final List<Distributor> distributors,
                       final List<Producer> producers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.producers = producers;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<Consumer> consumers) {
        this.consumers = consumers;
    }

    public List<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<Distributor> distributors) {
        this.distributors = distributors;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }
}
