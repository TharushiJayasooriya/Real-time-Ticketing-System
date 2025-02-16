import TicketDisplay from "./TicketDisplay.tsx";
import LogDisplay from "./LogDisplay.tsx";
import SimulationControl from "./ControlPanel.tsx";

function Body(){
    return(
    <>
    <h1>Ticketing System</h1>

        <TicketDisplay />
        <br /> <hr /> <br />
        <LogDisplay />
        <SimulationControl />

    </>
    );
}

export default Body;