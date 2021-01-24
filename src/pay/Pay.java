package pay;

import fileio.Consumer;
import fileio.ContractOutput;
import fileio.Distributor;

import java.util.List;
import java.util.Map;

public final class Pay {

    private static final Double PENALTY = 0.2;

    public Pay() {
    }

    /**
     *
     * @param consumer calculate budget for consumer
     */
    public void receivePay(final Consumer consumer) {
        int budget = consumer.getInitialBudget();
        consumer.setInitialBudget(budget + consumer.getMonthlyIncome());
    }

    /**
     *
     * @param distributor calculate budget for distributor
     */
    public void pay(final Distributor distributor) {
        int budget = distributor.getInitialBudget();
        distributor.setInitialBudget(budget - distributor.getInitialInfrastructureCost()
                - distributor.getInitialProductionCost() * distributor.getConsumers().size());
    }

    /**
     *
     * @param consumers for consumers
     * @param distributor and distributors calculate contract with different conditions
     */
    public void receivePay(final List<Consumer> consumers, final Distributor distributor) {
        for (Map.Entry<Integer, ContractOutput> consumer
                : distributor.getConsumers().entrySet()) {
            Integer id = consumer.getKey();
            Consumer c = consumers.get(id);
            ContractOutput co = consumer.getValue();
            int debt = c.getDebt();
            int oldPenalty = (int) (PENALTY * debt);
            Distributor debtToDistributor = c.getDebtToDistributor();
            int months = co.getRemainedContractMonths();
            int distributorBudget = distributor.getInitialBudget();
            int consumerBudget = c.getInitialBudget();
            int price = co.getPrice();
            int penalty = (int) (PENALTY * co.getPrice());
            if (c.getSw() == 1 && co.getRemainedContractMonths() >= 1) {
                if (consumerBudget >= 2 * price + penalty) {
                    c.setInitialBudget(consumerBudget - 2 * price - penalty);
                    distributor.setInitialBudget(distributorBudget + 2 * price + penalty);
                    co.setRemainedContractMonths(--months);
                } else {
                    c.setBankrupt(true);
                }
                continue;
            }
            if (debt != 0) {
                if (consumerBudget >= price + debt + oldPenalty) {
                    int oldBudget = debtToDistributor.getBudget();
                    c.setInitialBudget(consumerBudget - price - debt - oldPenalty);
                    distributor.setInitialBudget(distributorBudget + price);
                    debtToDistributor.setInitialBudget(oldBudget + debt + oldPenalty);
                    co.setRemainedContractMonths(--months);
                } else {
                    c.setBankrupt(true);
                }
                continue;
            }
            if (consumerBudget >= price) {
                c.setSw(0);
                c.setInitialBudget(consumerBudget - price);
                distributor.setInitialBudget(distributorBudget + price);
            } else {
                if (months == 1) {
                    c.setDebt(price);
                    c.setDebtToDistributor(distributor);
                } else {
                    c.setSw(1);
                }
            }
            co.setRemainedContractMonths(--months);
        }
    }
}

