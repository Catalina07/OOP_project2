package fileio;

import pay.Pay;
import strategies.ChoiceStrategy;
import strategies.StrategyInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Resolve {

    private static final Double PROFIT = 0.2;

    private static  final int DIVIDER = 10;

    private final Pay payInstance;

    public Resolve() {
        payInstance = new Pay();
    }

    /**
     *
     * @param consumers for a list of consumers
     * @param distributors for a list and distributors
     * @param producers for a list of producers calculate what happens the first month
     */
    public void firstMonth(final List<Consumer> consumers, final List<Distributor> distributors,
                           final List<Producer> producers) {
        for (int i = 0; i < distributors.size(); i++) {
            ChoiceStrategy strategy = new ChoiceStrategy();
            StrategyInterface type = strategy.createStrategy(distributors.get(i), producers);
            type.chooseDistributors(distributors.get(i), producers);
        }
        for (Distributor d : distributors) {
            int cost = 0;
            for (Producer p : d.getProducers()) {
                cost += p.getEnergyPerDistributor() * p.getPriceKW();
            }
            int productionCost = (int) Math.round(Math.floor(cost / DIVIDER));
            d.setInitialProductionCost(productionCost);
        }
        for (Consumer consumer : consumers) {
            payInstance.receivePay(consumer);
        }
        for (Distributor distributor : distributors) {
            int contract;
            contract = distributor.getInitialInfrastructureCost()
                    + distributor.getInitialProductionCost()
                    + (int) (distributor.getInitialProductionCost() * PROFIT);
            distributor.setContract(contract);
            //calculateContractCost(distributor);
        }
        for (Distributor distributor : distributors) {
            Map<Integer, ContractOutput> map = new LinkedHashMap<>();
            distributor.setConsumers(map);
        }
        Distributor d = distributors.stream().sorted().collect(Collectors.toList()).get(0);
        for (Consumer consumer : consumers) {
            d.getConsumers().put(consumer.getId(),
                    new ContractOutput(consumer.getId(), d.getContract(), d.getContractLength()));
        }
        payInstance.receivePay(consumers, d);
        for (Distributor distributor : distributors) {
            payInstance.pay(distributor);
        }
    }

    /**
     *
     * @param distributor for every distributor calculate number of consumers at end of round
     */
    public void calculateConsumersAtEndOfRound(final Distributor distributor) {
        distributor.setActiveConsumers(distributor.getConsumers().size());
    }

    /**
     *
     * @param distributor calculate the contract
     */
    public void calculateContractCost(final Distributor distributor) {
        int infrastructureCost = distributor.getInitialInfrastructureCost();
        int cost = 0;
        for (Producer p : distributor.getProducers()) {
            cost += p.getEnergyPerDistributor() * p.getPriceKW();
        }
        int productionCost = (int) Math.round(Math.floor(cost / DIVIDER));
        distributor.setInitialProductionCost(productionCost);
        int profit = (int) (Math.round(Math.floor(PROFIT * productionCost)));
        int activeConsumers = distributor.getActiveConsumers();
        if (activeConsumers == 0) {
            distributor.setContract(infrastructureCost + productionCost + profit);
        } else {
            distributor.setContract((infrastructureCost / activeConsumers)
                    + productionCost + profit);
        }
    }

    /**
     *
     * @param distributors delete contract
     */
    public void removeContracts(final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            List<Integer> removedConsumers = new ArrayList<>();
            for (Map.Entry<Integer, ContractOutput> consumer
                    : distributor.getConsumers().entrySet()) {
                if (consumer.getValue().getRemainedContractMonths() == 0) {
                    removedConsumers.add(consumer.getKey());
                }
            }
            for (Integer id : removedConsumers) {
                distributor.getConsumers().remove(id);
            }
        }
    }

    /**
     *
     * @param consumers for consumers
     * @param distributors and distributors calculate the contract
     */
    public void resolveContracts(final List<Consumer> consumers,
                                 final List<Distributor> distributors) {
        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                payInstance.receivePay(consumer);

                boolean hasContract = false;
                for (Distributor d : distributors) {
                    if (!d.isBankrupt() && d.getConsumers().containsKey(consumer.getId())) {
                        hasContract = true;
                        break;
                    }
                }
                if (!hasContract) {
                    List<Distributor> orderedDistributors = distributors
                            .stream()
                            .filter(d -> !d.isBankrupt())
                            .sorted().collect(Collectors.toList());
                    if (orderedDistributors.size() != 0) {
                        Distributor newDistributor = orderedDistributors.get(0);
                        newDistributor.getConsumers().put(consumer.getId(),
                                new ContractOutput(consumer.getId(), newDistributor.getContract(),
                                        newDistributor.getContractLength()));
                    }
                }
            }
        }
    }

    /**
     *
     * @param consumers for consumers
     * @param distributors and distributors calculate in case there are not bankrupt
     */
    public void allPays(final List<Consumer> consumers, final List<Distributor> distributors) {
        for (Distributor d : distributors) {
            if (!d.isBankrupt()) {
                payInstance.receivePay(consumers, d);
                payInstance.pay(d);
            }
        }
    }

    /**
     *
     * @param distributors see if it s bankrupt
     */
    public void bankruptDistributors(final List<Distributor> distributors) {
        for (Distributor d : distributors) {
            if (d.getInitialBudget() < 0) {
                d.setBankrupt(true);
            }
        }
    }

    /**
     *
     * @param consumers for consumers
     * @param distributors and for distributors see the case when we have a bankrupt consumer
     */
    public void bankruptConsumers(final List<Consumer> consumers,
                                  final List<Distributor> distributors) {
        for (Distributor distributor : distributors) {
            List<Integer> removedConsumers = new ArrayList<>();
            for (Map.Entry<Integer, ContractOutput> consumer
                    : distributor.getConsumers().entrySet()) {
                if (consumers.get(consumer.getKey()).isBankrupt()) {
                    removedConsumers.add(consumer.getKey());
                }
            }
            for (Integer id : removedConsumers) {
                distributor.getConsumers().remove(id);
            }
        }
    }

    /**
     *
     * @param producers for producers
     * @param month and each moth, aplicate changes
     */
    public void monthly(final List<Producer> producers, final int month) {
        for (Producer p : producers) {
            MonthlyStats monthlyStats = new MonthlyStats();
            monthlyStats.setMonth(month);
            List<Integer> ids = new ArrayList<>();
            for (Distributor d : p.getDistributors()) {
                ids.add(d.getId());
            }
            ids = ids.stream().sorted(Comparator.comparing(Integer::intValue))
                    .collect(Collectors.toList());
            monthlyStats.setDistributorsIds(ids);
            p.getMonthlyStats().add(monthlyStats);
        }
    }
}
