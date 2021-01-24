import com.fasterxml.jackson.databind.ObjectMapper;
import factory.OutputFactory;
import fileio.Consumer;
import fileio.ConsumersOutput;
import fileio.Distributor;
import fileio.DistributorChanges;
import fileio.DistributorsOutput;
import fileio.Input;
import fileio.MonthlyUpdates;
import fileio.NewConsumers;
import fileio.Output;
import fileio.Producer;
import fileio.ProducerChanges;
import fileio.ProducersOutput;
import fileio.Resolve;
import strategies.ChoiceStrategy;
import strategies.StrategyInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Entry point to the simulation
 */
public final class Main {

    private static final String CONSUMER = "consumer";
    private static final String DISTRIBUTOR = "distributor";
    private static final String PRODUCER = "producer";

    private Main() { }

    /**
     * Main function which reads the input file and starts simulation
     *
     * @param args input and output files
     * @throws Exception might error when reading/writing/opening files, parsing JSON
     */
    public static void main(final String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];

        ObjectMapper mapper = new ObjectMapper();
        Input input = mapper.readValue(new File(inputFile), Input.class);
        List<Consumer> consumers = input.getInitialData().getConsumers();
        List<Distributor> distributors = input.getInitialData().getDistributors();
        List<Producer> producers = input.getInitialData().getProducers();

        Resolve resolve = new Resolve();
        resolve.firstMonth(consumers, distributors, producers);
        for (Distributor d : distributors) {
            resolve.calculateConsumersAtEndOfRound(d);
        }

        for (int i = 0; i < input.getNumberOfTurns(); i++) {
            MonthlyUpdates monthlyUpdates = input.getMonthlyUpdates().get(i);
            List<NewConsumers> newConsumers = monthlyUpdates.getNewConsumers();
            for (NewConsumers newCons : newConsumers) {
                consumers.add(new Consumer(newCons.getId(),
                        newCons.getInitialBudget(), newCons.getMonthlyIncome()));
            }

            List<DistributorChanges> distributorChanges = monthlyUpdates.getDistributorChanges();
            for (DistributorChanges cost : distributorChanges) {
                distributors.get(cost.getId())
                        .setInitialInfrastructureCost(cost.getInfrastructureCost());
                distributors.get(cost.getId())
                        .setInitialProductionCost(cost.getProductionCost());
            }

            for (Distributor d : distributors) {
                resolve.calculateContractCost(d);
            }
                List<ProducerChanges> prodChanges = monthlyUpdates.getProducerChanges();
                for (ProducerChanges pChanges : prodChanges) {
                    producers.get(pChanges.getId()).setChanges(pChanges.getEnergyPerDistributor());
                }
            List<Distributor> distr = distributors.stream().sorted(Comparator
                    .comparing(Distributor::getId)).collect(Collectors.toList());
            for (Distributor dist : distr) {
                if (dist.getSeeChanges()) {
                    for (int k = 0; k < dist.getProducers().size(); k++) {
                        dist.getProducers().get(k).deleteObserver(dist);
                        dist.getProducers().get(k).getDistributors().remove(dist);
                    }
                    dist.getProducers().clear();
                    ChoiceStrategy strategy = new ChoiceStrategy();
                    StrategyInterface type = strategy.createStrategy(dist, producers);
                    type.chooseDistributors(dist, producers);
                }
            }
            resolve.removeContracts(distributors);
            resolve.resolveContracts(consumers, distributors);
            resolve.allPays(consumers, distributors);

            resolve.bankruptDistributors(distributors);
            resolve.bankruptConsumers(consumers, distributors);

            for (Distributor d : distributors) {
                resolve.calculateConsumersAtEndOfRound(d);
            }
            resolve.monthly(producers, i + 1);
        }

        List<ConsumersOutput> consumersOutputs = new ArrayList<>();
        List<DistributorsOutput> distributorsOutputs = new ArrayList<>();
        List<ProducersOutput> producersOutputs = new ArrayList<>();

        for (Consumer c : consumers) {
            consumersOutputs.add((ConsumersOutput) OutputFactory
                    .getFactory().createEntityOutput(c, CONSUMER));
        }
        for (Distributor d : distributors) {
            distributorsOutputs.add((DistributorsOutput) OutputFactory
                    .getFactory().createEntityOutput(d, DISTRIBUTOR));
        }
        for (Producer p : producers) {
            producersOutputs.add((ProducersOutput) OutputFactory.getFactory()
                    .createEntityOutput(p, PRODUCER));
        }

        Output output = new Output(consumersOutputs, distributorsOutputs, producersOutputs);

       BufferedWriter file = Files.newBufferedWriter(Path.of(outputFile));
       file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(output));
       file.flush();
       file.close();
    }
}
