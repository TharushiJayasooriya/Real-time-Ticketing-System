package OOP.java.Real.Time_Ticketing.System.Example.Controller;


import OOP.java.Real.Time_Ticketing.System.Example.Model.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @CrossOrigin(origins = "*")
@RequestMapping("/api/simulation")
public class SimulationController {

    @PostMapping("/start")
    public void startSimulation() {
        Transaction.main(new String[]{});
    }

    @PostMapping("/stop")
    public void stopSimulation() {
        Transaction.vendorThread.interrupt();
        Transaction.customerThread.interrupt();
    }
}
