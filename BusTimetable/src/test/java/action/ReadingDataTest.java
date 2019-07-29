package action;

import by.duallab.timetable.Bus;
import by.duallab.timetable.Reader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Class tests reading data from file.
 */
public class ReadingDataTest {

    private Reader reader = new Reader();

    @DataProvider(name = "dataProviderForReader")
    public Object[] dataProviderForReader() {
        return new Object[][]{{"src\\main\\resources\\data\\input.txt", 8}};
    }

    @Test(description = "test positive reading of cube data",
            dataProvider = "dataProviderForReader")
    public void testReader(String file, int expectedSize) {
        List<Bus> actual = reader.read(file);
        Assert.assertEquals(actual.size(), expectedSize);
    }
}
