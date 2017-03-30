/**
 * Created by Horica on 3/30/2017.
 */

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MainTest {

    private static List<Vehicle> vehicleList;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        String json = Main.readUrl("http://www.rentalcars.com/js/vehicles.json");
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(json);
        JsonObject search = jo.getAsJsonObject("Search");
        JsonArray jsonArr = search.getAsJsonArray("VehicleList");
        Gson gson = new Gson();
        Vehicle[] vehicles = gson.fromJson(jsonArr, Vehicle[].class);
        vehicleList = Arrays.asList(vehicles);
    }



    @Test
    public void testSortByPrice() {
        Main.sortByPrice(vehicleList);
        for (int i = 1; i < vehicleList.size(); i++) {
            assertTrue(vehicleList.get(i-1).getPrice() <= vehicleList.get(i).getPrice());
        }
    }

    @Test
    public void testSortByScore() {
        Main.sortByScore(vehicleList);
        for (int i = 1; i < vehicleList.size(); i++) {
            assertTrue(vehicleList.get(i-1).getScore() + vehicleList.get(i-1).getRating()
                       >= (vehicleList.get(i).getScore() + vehicleList.get(i).getRating()));
        }
    }

   @Test
    public void testGetHighestRatedSupplierPerCarType() {
        List<Vehicle> testList = Main.getHighestRatedSupplierPerCarType(vehicleList);
        for (int i = 1; i < testList.size(); i++) {
            assertTrue(testList.get(i-1).getRating() >= testList.get(i).getRating());
        }
    }
}
