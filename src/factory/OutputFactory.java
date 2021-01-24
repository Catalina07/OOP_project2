package factory;

import fileio.Consumer;
import fileio.ConsumersOutput;
import fileio.ContractOutput;
import fileio.Distributor;
import fileio.DistributorsOutput;
import fileio.EntityInput;
import fileio.EntityOutput;
import fileio.Producer;
import fileio.ProducersOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OutputFactory {
    private static final String CONSUMER = "consumer";
    private static final String DISTRIBUTOR = "distributor";
    private static final String PRODUCER = "producer";
    private static OutputFactory factory = null;

    private OutputFactory() { }

    /**
     *
     * @return for Singleton
     */
    public static OutputFactory getFactory() {
        if (factory == null) {
            factory = new OutputFactory();
        }
        return factory;
    }

    /**
     *
     * @param entityInput create entity
     * @param input for data from input
     * @return return output
     */
    public EntityOutput createEntityOutput(final EntityInput entityInput, final String input) {
        switch (input) {
            case CONSUMER -> {
                Consumer c = (Consumer) entityInput;
                return new ConsumersOutput(c.getId(), c.isBankrupt(), c.getInitialBudget());
            }
            case DISTRIBUTOR -> {
                Distributor d = (Distributor) entityInput;
                List<ContractOutput> co = new ArrayList<>();
                if (d.getConsumers().values().size() != 0) {
                    for (Map.Entry<Integer, ContractOutput> consumer
                            : d.getConsumers().entrySet()) {
                        co.add(consumer.getValue());
                    }
                }
                return new DistributorsOutput(d.getId(), d.getInitialBudget(), d.isBankrupt(), co,
                        d.getEnergyNeededKW(), d.getContract(), d.getProducerStrategy());
            }
            case PRODUCER -> {
                Producer p = (Producer) entityInput;
                return new ProducersOutput(p.getId(), p.getMaxDistributors(), p.getPriceKW(),
                        p.getEnergyType(), p.getEnergyPerDistributor(), p.getMonthlyStats());
            }
            default ->  {
                return null;
            }
        }
    }
}
