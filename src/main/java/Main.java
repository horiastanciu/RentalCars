import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.utils.IOUtils;
import com.google.gson.Gson;

import static spark.Spark.*;

public class Main {
    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }


    public static void sortByPrice(List<Vehicle> vehicles) {
        java.util.Collections.sort(vehicles, new Comparator<Vehicle>() {
            public int compare(Vehicle a, Vehicle b) {
                return (int)(a.getPrice() - b.getPrice());
            }
            public boolean equals(Object a) {
                return false;
            }
        });
    }

    public static void sortByScore(List<Vehicle> vehicles) {
        java.util.Collections.sort(vehicles, new Comparator<Vehicle>() {
            public int compare(Vehicle a, Vehicle b) {
                double aFinalScore = a.getScore() + a.getRating();
                double bFinalScore = b.getScore() + b.getRating();
                if(aFinalScore > bFinalScore){
                    return -1;
                }else if(aFinalScore < bFinalScore) {
                    return 1;
                }
                return 0;
                //return (int)(b.getScore() + b.getRating() - a.getScore() - a.getRating());
            }
            public boolean equals(Object a) {
                return false;
            }
        });
    }

    public static List<Vehicle> getHighestRatedSupplierPerCarType(List<Vehicle> vehicleList)  {
        Map<String, Double> mapCarTypeRating = new HashMap<>();
        Set<Vehicle> set = new HashSet<>();
        for (Vehicle vehicle : vehicleList) {
            if ( !mapCarTypeRating.containsKey(vehicle.getCarType()) || vehicle.getRating() > mapCarTypeRating.get(vehicle.getCarType()) ) {
                mapCarTypeRating.put(vehicle.getCarType(), vehicle.getRating());
                //check if vehicle with same cartype exists in set and remove it
                for(Vehicle v : set){
                    if(v.getCarType() == vehicle.getCarType()){
                        set.remove(v);
                        break;
                    }
                }
                set.add(vehicle);
            }
        }
        List<Vehicle> list = new ArrayList<>();
        list.addAll(set);
        return list;
    }

    public static void main(String[] args) throws Exception {
        String json = readUrl("http://www.rentalcars.com/js/vehicles.json");
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(json);
        JsonObject search = jo.getAsJsonObject("Search");
        JsonArray jsonArr = search.getAsJsonArray("VehicleList");
        Gson gson = new Gson();
        Vehicle[] vehicles = gson.fromJson(jsonArr, Vehicle[].class);
        List<Vehicle> vehicleList = Arrays.asList(vehicles);
        StringBuffer part1String = new StringBuffer("");
        StringBuffer part2String = new StringBuffer("");
        StringBuffer part3String = new StringBuffer("");
        StringBuffer part4String = new StringBuffer("");

        int counter = 1;

//        // PART 1.1
        sortByPrice(vehicleList);
        for (Vehicle vehicle : vehicleList) {
            String currentString = counter + ". " + vehicle.part1();
            System.out.println(currentString);
            part1String.append(currentString);
            part1String.append("<br>");
            counter++;
        }

//        // PART 1.2
        counter = 1;
        for (Vehicle vehicle : vehicleList) {
            String currentString = counter + ". " + vehicle.part2();
            System.out.println(currentString);
            part2String.append(currentString);
            part2String.append("<br>");
            counter++;
        }

        //PART 1.3
        counter = 1;
        List<Vehicle> list = getHighestRatedSupplierPerCarType(vehicleList);
        for (Vehicle vehicle : list) {
            String currentString = counter + ". " + vehicle.part3();
            System.out.println(currentString);
            part3String.append(currentString);
            part3String.append("<br>");
            counter++;
        }

        //PART 1.4
        sortByScore(vehicleList);
        counter = 1;
        for (Vehicle vehicle : vehicleList) {
            String currentString = counter + ". " + vehicle.part4();
            System.out.println(currentString);
            part4String.append(currentString);
            part4String.append("<br>");
            counter++;
        }


//        Map<String, Integer> map = search.getMapSupplierScores();
//        for (Vehicle vehicle : vehicleList) {
//            System.out.println(vehicle.part4() + " - {" + vehicle.getSupplier() + "} - {"
//                               + map.get(vehicle.getSupplier()) + "}");
//        }

        init();
        get("/RentalCars/:name", (request, response) -> {
            switch(request.params(":name")){
                case ("part1"): return part1String;
                case ("part2"): return part2String;
                case ("part3"): return part3String;
                case ("part4"): return part4String;
            }
            return "Hello: " + request.params(":name");
        });

    }
}
