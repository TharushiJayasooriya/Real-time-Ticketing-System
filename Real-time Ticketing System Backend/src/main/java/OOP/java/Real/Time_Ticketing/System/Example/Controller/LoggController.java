package OOP.java.Real.Time_Ticketing.System.Example.Controller;


import OOP.java.Real.Time_Ticketing.System.Example.repositories.LoggTable;
import OOP.java.Real.Time_Ticketing.System.Example.repositories.LoggTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping("/api/logs")
public class LoggController {

    @Autowired
    private LoggTableRepository loggTableRepository;

    /**
     * Posting the log to the log table in tha database
     * @param logMessage
     * @return
     */
    @PostMapping
    public ResponseEntity<String> postLog(@RequestBody String logMessage) {
        LoggTable log = new LoggTable();
        log.setMsg(logMessage);
        loggTableRepository.save(log);
        return ResponseEntity.ok("Log saved successfully!");
    }

    /**
     * Get request for the logs
     * @return
     */
    @GetMapping
    public List<LoggTable> getAllLogs(){
        return loggTableRepository.findAll();
    }


}