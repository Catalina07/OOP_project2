package strategies;

import fileio.Distributor;
import fileio.Producer;

import java.util.List;

public final class ChoiceStrategy {
    private static ChoiceStrategy instance = null;

    /**
     *
     * @return used for Singleton
     */
    public static ChoiceStrategy getInstance() {
        if (instance == null) {
            instance = new ChoiceStrategy();
        }
        return instance;
    }

    /**
     *
     * @param distributor for every distributor
     * @param producers for a list of producers
     * @return choose a strategy
     */
    public StrategyInterface createStrategy(Distributor distributor, List<Producer> producers) {
        if (distributor.getProducerStrategy().equals("GREEN")) {
            return new GreenStrategy(distributor, producers);
        }

        if (distributor.getProducerStrategy().equals("PRICE")) {
            return new PriceStrategy(distributor, producers);
        }
            return new QuantityStrategy(distributor, producers);
    }
}
