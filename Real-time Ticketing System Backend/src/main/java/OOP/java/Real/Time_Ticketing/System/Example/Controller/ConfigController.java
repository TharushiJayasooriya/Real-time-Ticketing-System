package OOP.java.Real.Time_Ticketing.System.Example.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    private final Map<String, Object> config;

    public ConfigController() {
        try {
            // JSON file path
            String filePath = "C:\\Users\\anuja\\Downloads\\Ticket System 10th Dec-Anu\\config.json";
            ObjectMapper objectMapper = new ObjectMapper();
            this.config = objectMapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }

    @GetMapping
    public Map<String, Object> getConfig() {
        return config;
    }
}
