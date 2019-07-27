import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String PATH_TO_OUTPUT_FILE
            = "src\\main\\resources\\data\\output.txt";

    public static void main(final String[] args) {
        Reader reader = new Reader();
        Writer writer = new Writer();
        List<Bus> buses = reader.read(args[0]);

        List<Bus> bestBuses = new ArrayList<>();
        bestBuses.add(buses.get(0));
        boolean isEqual;
        List<Integer> indexList = new ArrayList<>();

        LOGGER.debug("Calculation of timetable started.");
        for (int i = 1; i < buses.size(); i++) {
            isEqual = calculation(bestBuses, buses, i, indexList);

            if (!indexList.isEmpty()) {
                for (int k : indexList) {
                    bestBuses.set(k, buses.get(i));
                }
                indexList.clear();
            } else {
                if (!isEqual) {
                    bestBuses.add(buses.get(i));
                }
            }
        }
        LOGGER.debug("Calculation of timetable ended.");

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

    private static boolean calculation(final List<Bus> bestBuses,
                                       final List<Bus> buses,
                                       final int i,
                                       final List<Integer> indexList) {
        boolean isAppropriate = false;
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
                            isAppropriate = true;
                        }
                    }
                }
            }
        }

        return isAppropriate;
    }
}
