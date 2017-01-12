package tests;

import net.webservicex.Lengths;
import net.webservicex.TemperatureUnit;
import org.junit.Test;
import wsdl2java.CxfClient;

import static org.junit.Assert.assertTrue;

public class SimpleTest {

    @Test
    public void lengthTest() {
        assertTrue(CxfClient.getLengthClient().changeLengthUnit(1, Lengths.KILOMETERS, Lengths.METERS) == 1000);
    }

    @Test
    public void temperatureTest() {
        assertTrue(CxfClient.getTemperatureClient().convertTemp(10, TemperatureUnit.DEGREE_CELSIUS, TemperatureUnit.DEGREE_RANKINE) == 8);
    }

}
