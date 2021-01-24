package strategies;

import fileio.Distributor;
import fileio.Producer;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class GreenStrategy implements StrategyInterface {
    private Distributor distributorList;
    private List<Producer> producersList;

    public GreenStrategy(Distributor distributor, List<Producer> producers) {
        this.distributorList = distributor;
        this.producersList = producers;
    }

    @Override
    public void chooseDistributors(Distributor distributor, List<Producer> producers) {
        List<Producer> prod = producers.stream()
                .sorted(Comparator.comparing(Producer::decideEnergy, Comparator.reverseOrder())
                        .thenComparing(Producer::getPriceKW)
        .thenComparing(Producer::getEnergyPerDistributor, Comparator.reverseOrder())
                        .thenComparing(Producer::getId)).collect(Collectors.toList());

        int energyNeeded = 0;
        int i = 0;
        while (energyNeeded < distributor.getEnergyNeededKW()) {
            if (prod.get(i).getDistributors().size() < prod.get(i).getMaxDistributors()) {
                energyNeeded += prod.get(i).getEnergyPerDistributor();
                distributor.getProducers().add(prod.get(i));
                prod.get(i).getDistributors().add(distributor);
                prod.get(i).addObserver(distributor);
            }
            i++;
        }
    }

}
