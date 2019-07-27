import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Reader {

    private static final Logger LOGGER = LogManager.getLogger(Reader.class);
    private static final String INCORRECT_DATA =
            "File has incorrect data, please refactor your file.";
    private static final String FILE_NOT_FOUND =
            "File wasn't found. Please, check name of the file.";
    private static final String INCORRECT_FILE =
            "File can not be closed.";
    private List<Bus> buses = new ArrayList<>();

    public List<Bus> read(final String pathToFile) {
        String line;
        BufferedReader bufferedReader = null;
        try (FileReader fileReader = new FileReader(new File(pathToFile))) {
            bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    buses.add(parseString(line));
                }
            }
        } catch (FileNotFoundException ex) {
            LOGGER.error(FILE_NOT_FOUND);
        } catch (IOException ex) {
            LOGGER.error(INCORRECT_DATA);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOGGER.error(INCORRECT_FILE);
            }
        }

        return buses;
    }

    private Bus parseString(final String string) {
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, " :");
        Bus bus = new Bus();
        bus.setNameOfBus(stringTokenizer.nextToken());
        bus.setDepartureTime(LocalTime.of(
                Integer.parseInt(stringTokenizer.nextToken()),
                Integer.parseInt(stringTokenizer.nextToken())));
        bus.setArrivalTime(LocalTime.of(
                Integer.parseInt(stringTokenizer.nextToken()),
                Integer.parseInt(stringTokenizer.nextToken())));
        return bus;

    }
}
