package by.duallab.timetable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * by.duallab.timetable.Writer is class which write information to the file.
 *
 * @author Roman
 * @version 1.0
 */
public class Writer {

    /**
     * Logger for adding logs about execution of methods.
     */
    private static final Logger LOGGER = LogManager.getLogger(Writer.class);
    /**
     * Message for incorrect data.
     */
    private static final String INCORRECT_FILE =
            "File can not be closed.";

    /**
     * Writes data to the file.
     *
     * @param pathToFile - path to the specified file
     * @param buses      - buses which should be written to the file
     */
    public void write(final String pathToFile, final List<Bus> buses) {
        try (FileWriter fileWriter = new FileWriter(new File(pathToFile))) {
            String lastName = buses.get(0).getNameOfBus();
            LOGGER.debug("Writing started.");
            for (Bus bus : buses) {
                if (!bus.getNameOfBus().equals(lastName)) {
                    fileWriter.write("\n");
                    lastName = bus.getNameOfBus();
                }
                fileWriter.write(bus.toString() + "\n");
            }
            LOGGER.debug("Writing ended correctly.");
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.error(INCORRECT_FILE);
        }

    }
}
