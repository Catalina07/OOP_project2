package strategies;

import fileio.Distributor;
import fileio.Producer;

import java.util.List;

public interface StrategyInterface {

    /**
     *
     * @param distributor for each distributor
     * @param producers create a list respecting different conditions
     */
    void chooseDistributors(Distributor distributor, List<Producer> producers);
}
