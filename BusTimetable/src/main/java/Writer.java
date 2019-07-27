import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer {

    private static final Logger LOGGER = LogManager.getLogger(Writer.class);
    private static final String INCORRECT_FILE =
            "File can not be closed.";

    public void write(final String pathToFile, final List<Bus> buses) {
        try (FileWriter fileWriter = new FileWriter(new File(pathToFile))) {
            String lastName = buses.get(0).getNameOfBus();

            for (Bus bus : buses) {
                if (!bus.getNameOfBus().equals(lastName)) {
                    fileWriter.write("\n");
                    lastName = bus.getNameOfBus();
                }
                fileWriter.write(bus.toString() + "\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            LOGGER.error(INCORRECT_FILE);
        }

    }
}
