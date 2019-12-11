package by.duallab.timetable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * by.duallab.timetable.Reader is class which gets information from file.
 *
 * @author Roman
 * @version 1.0
 */
public class Reader {
    /**
     * Logger for adding logs about execution of methods.
     */
    private static final Logger LOGGER = LogManager.getLogger(Reader.class);
    /**
     * Message for incorrect data.
     */
    private static final String INCORRECT_DATA =
            "File has incorrect data, please refactor your file.";
    /**
     * Message for incorrect name of file.
     */
    private static final String FILE_NOT_FOUND =
            "File wasn't found. Please, check name of the file.";
    /**
     * Message for incorrect file.
     */
    private static final String INCORRECT_FILE =
            "File can not be closed.";

    /**
     * Message before stack trace.
     */
    private static final String STACK_TRACE =
            " Stack trace: ";

    /**
     * List with buses from file.
     */
    private List<Bus> buses = new ArrayList<>();

    /**
     * Reads data from file.
     *
     * @param pathToFile - path to file with data
     * @return buses from file
     */
    public List<Bus> read(final String pathToFile) {
        String line;
        BufferedReader bufferedReader = null;
        try (FileReader fileReader = new FileReader(new File(pathToFile))) {
            bufferedReader = new BufferedReader(fileReader);
            LOGGER.debug("Reading started.");
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    buses.add(parseString(line));
                }
            }
            LOGGER.debug("Reading ended correctly.");
        } catch (FileNotFoundException ex) {
            LOGGER.error(FILE_NOT_FOUND + STACK_TRACE
                    + Arrays.toString(ex.getStackTrace()));
        } catch (IOException ex) {
            LOGGER.error(INCORRECT_DATA + STACK_TRACE
                    + Arrays.toString(ex.getStackTrace()));
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                LOGGER.error(INCORRECT_FILE + STACK_TRACE
                        + Arrays.toString(e.getStackTrace()));
            }
        }

        return buses;
    }

    /**
     * Creates object of empty class {@code by.duallab.timetable.Bus}.
     *
     * @param string - string with information about bus
     * @return object of {@code by.duallab.timetable.Bus} class
     */
    private Bus parseString(final String string) {
        StringTokenizer stringTokenizer
                = new StringTokenizer(string, " ");
        Bus bus = new Bus();
        bus.setNameOfBus(stringTokenizer.nextToken());
        bus.setDepartureTime(LocalTime.parse(stringTokenizer.nextToken()));
        bus.setArrivalTime(LocalTime.parse(stringTokenizer.nextToken()));
        return bus;

    }
}
