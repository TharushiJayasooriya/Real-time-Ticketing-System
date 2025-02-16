import  { useEffect, useState } from 'react';

const TicketDisplay = () => {
    const [totalTickets, setTickets] = useState<number>(0);
    const [maxTicketCapacity, setMaxCapacity] = useState<number>(0);
    const [ticketReleaseRate, setTicketReleaseRate] = useState<number>(0);
    const [customerRetrievalRate, setCustomerRetrievalRate] = useState<number>(0);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        // Define the function to fetch ticket data
        const fetchTicketData = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/config');
                if (!response.ok) {
                    setError("Failed to fetch ticket data." +
                        " Either there is a bad endpoint or the database is not " +
                        "currently running."); // Error to be displayed
                }
                else {
                    setError(""); // clear the Error string once error is solved
                }

                const data = await response.json();  // Response comes in Json format

                // Set the state with the table data
                setTickets(data["totalTickets"]);
                setMaxCapacity(data["maxTicketCapacity"]);
                setTicketReleaseRate(data["ticketReleaseRate"]);
                setCustomerRetrievalRate(data["customerRetrievalRate"]);
            } catch (error) {
                console.error(error);
            }
        };


        // Periodic polling
        const interval = setInterval(fetchTicketData, 2000); // Fetch every 2 seconds

        return () => clearInterval(interval);
    }, []);

    return (
        <>
            <h2>Total available tickets and other information</h2>
            <p className="errorTags">{error}</p>

            <table>
                <thead>
                <tr>
                    <th>Total Tickets</th>
                    <th>Max Capacity</th>
                    <th>Ticket Release Rate</th>
                    <th>Customer Retrieval Rate</th>
                </tr>
                <tr>

                    <td>{totalTickets}</td>
                    <td>{maxTicketCapacity}</td>
                    <td>{ticketReleaseRate}</td>
                    <td>{customerRetrievalRate}</td>
                </tr>

                </thead>
            </table>
        </>
    );
};

export default TicketDisplay;
