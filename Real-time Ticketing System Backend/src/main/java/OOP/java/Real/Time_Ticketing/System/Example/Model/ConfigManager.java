package OOP.java.Real.Time_Ticketing.System.Example.Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.json";

    public static JSONObject loadConfigs(){
        File configFile = new File(CONFIG_FILE);

        if (!configFile.exists()) {
            System.out.println("Config file does not exist");
            createNewConfiguration();
        }
        try (FileReader reader=new FileReader(configFile)){
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(reader);
        }catch (IOException | org.json.simple.parser.ParseException e){
            System.out.println("Error reading config file"+e.getMessage());
            throw new RuntimeException();
        }

    }
    public static JSONObject createNewConfiguration() {
        JSONObject config = new JSONObject();
        Scanner scanner=new Scanner(System.in);

        System.out.println("enter total tickets: ");
        config.put("totalTickets", validateInput(scanner));

        System.out.println("enter ticket release rate: ");
        config.put("ticketReleaseRate", validateInput(scanner));

        System.out.println("enter customer retrieval rate: ");
        config.put("customerRetrievalRate", validateInput(scanner));

        System.out.println("enter maximum ticket Capacity: ");
        config.put("maxTicketCapacity", validateInput(scanner));

        saveConfiguration(config);
        return config;
    }
    private static void saveConfiguration(JSONObject config) {
        try (FileWriter writer=new FileWriter(CONFIG_FILE)){
            writer.write(config.toJSONString());
            System.out.println("Saved config file");

        }catch (IOException e){
            System.out.println("Error writing config file"+e.getMessage());
        }
    }
    private static int validateInput(Scanner scanner) {
        int value;
        while(true){

            try {
                value=Integer.parseInt(scanner.next());
                if (value >= 0) {
                    break;
                }else {
                    System.out.println("Please enter a value greater than or equal to 0");
                }

            }catch (NumberFormatException e){
                System.out.println("Invalid input. please try again");
            }
        }
        return value;
    }

}
