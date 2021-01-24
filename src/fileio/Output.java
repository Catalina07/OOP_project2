package fileio;

import java.util.List;

public final class Output {

    private List<ConsumersOutput> consumers;

    private List<DistributorsOutput> distributors;
    private List<ProducersOutput> energyProducers;

    public List<ProducersOutput> getEnergyProducers() {
        return energyProducers;
    }

    public void setProducers(List<ProducersOutput> energyProducers) {
        this.energyProducers = energyProducers;
    }

    public Output(final List<ConsumersOutput> consumers,
                  final List<DistributorsOutput> distributors,
                  final List<ProducersOutput> energyProducers) {
        this.consumers = consumers;
        this.distributors = distributors;
        this.energyProducers = energyProducers;
    }

    public List<ConsumersOutput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumersOutput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorsOutput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorsOutput> distributors) {
        this.distributors = distributors;
    }

}
