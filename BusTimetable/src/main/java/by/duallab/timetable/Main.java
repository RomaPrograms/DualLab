package by.duallab.timetable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * by.duallab.timetable.Main class for execution program.
 *
 * @author Roman
 * @version 1.0
 */
public class Main {
    /**
     * Logger for adding logs about execution of methods.
     */
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    /**
     * Path to output file.
     */
    private static final String PATH_TO_OUTPUT_FILE
            = "src\\main\\resources\\data\\output.txt";

    /**
     * Combines all methods for creating correct and sorted timetable.
     *
     * @param args - contain path to the file with data
     */
    public static void main(final String[] args) {
        Reader reader = new Reader();
        Writer writer = new Writer();
        List<Bus> buses = reader.read(args[0]);

        List<Bus> bestBuses = new ArrayList<>();
        bestBuses.add(buses.get(0));

        creatingCorrectTimetable(bestBuses, buses);

        bestBuses.sort((o1, o2) -> {
            if (o1.getNameOfBus().compareTo(o2.getNameOfBus()) < 0) {
                return 1;
            }

            if (o1.getNameOfBus().compareTo(o2.getNameOfBus()) > 0) {
                return -1;
            }

            return o1.getArrivalTime().compareTo(o2.getArrivalTime());
        });

        writer.write(PATH_TO_OUTPUT_FILE, bestBuses);
    }

    /**
     * Determines correct buses for timetable.
     *
     * @param bestBuses - list with correct buses
     * @param buses     - list with incorrect buses
     */
    public static void creatingCorrectTimetable(final List<Bus> bestBuses,
                                                 final List<Bus> buses) {
        List<Integer> indexList = new ArrayList<>();
        LOGGER.debug("Calculation of timetable started.");
        boolean isEqual;
        for (int i = 1; i < buses.size(); i++) {
            isEqual = calculation(bestBuses, buses, i, indexList);

            if (!indexList.isEmpty()) {
                for (int k : indexList) {
                    bestBuses.set(k, buses.get(i));
                }
                indexList.clear();
            } else {
                if (isEqual) {
                    bestBuses.add(buses.get(i));
                }
            }
        }
        LOGGER.debug("Calculation of timetable ended.");
    }

    /**
     * Calculates to determine if a particular train is correct.
     *
     * @param bestBuses - list with best buses for correct timetable
     * @param buses     - list with all buses
     * @param i         - index of current bus among all buses
     * @param indexList - list with indexes of buses in {@code bestBuses} list
     *                  which can be replaced by current(particular) bus
     * @return {@code true} if bus is appropriate and {@code false} otherwise
     */
    private static boolean calculation(final List<Bus> bestBuses,
                                       final List<Bus> buses,
                                       final int i,
                                       final List<Integer> indexList) {
        boolean isAppropriate = true;
        for (int j = 0; j < bestBuses.size(); j++) {
            if (buses.get(i).getDepartureTime().equals(bestBuses.get(j).getDepartureTime())
                    && buses.get(i).getArrivalTime().isBefore(bestBuses.get(j).getArrivalTime())) {
                indexList.add(j);
            } else {
                if (buses.get(i).getDepartureTime().isAfter(bestBuses.get(j).getDepartureTime())
                        && buses.get(i).getArrivalTime().equals(bestBuses.get(j).getArrivalTime())) {
                    indexList.add(j);
                } else {
                    if (buses.get(i).getDepartureTime().isAfter(bestBuses.get(j).getDepartureTime())
                            && buses.get(i).getArrivalTime().isBefore(bestBuses.get(j).getArrivalTime())) {
                        indexList.add(j);
                    } else {
                        if (buses.get(i).getDepartureTime().equals(bestBuses.get(j).getDepartureTime())
                                || buses.get(i).getArrivalTime().equals(bestBuses.get(j).getArrivalTime())
                                || (buses.get(i).getDepartureTime().isBefore(bestBuses.get(j).getDepartureTime())
                                && buses.get(i).getArrivalTime().isAfter(bestBuses.get(j).getArrivalTime()))) {
                            isAppropriate = false;
                        }
                    }
                }
            }
        }

        return isAppropriate;
    }
}
