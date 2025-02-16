import React, { useState } from 'react';
import axios from 'axios';

const SimulationControl: React.FC = () => {
    const [isRunning, setIsRunning] = useState(false);


    // Start and stop simulation as before
    const handleStart = async () => {
        try {
            await axios.post('http://localhost:8081/api/simulation/start');
            setIsRunning(true);
        } catch (error) {
            console.error('Error starting the simulation', error);
        }
    };

    const handleStop = async () => {
        try {
            await axios.post('http://localhost:8081/api/simulation/stop');
            setIsRunning(false);
        } catch (error) {
            console.error('Error stopping the simulation', error);
        }
    };

    return (
        <div>
            <br/><hr /><br/>
            <h2>Simulation Control</h2>
            <button onClick={handleStart} disabled={isRunning}>
                Start Simulation
            </button>
            <button onClick={handleStop} disabled={!isRunning}>
                Stop Simulation
            </button>
            <p>{isRunning ? 'Simulation is running' : 'Simulation is stopped'}</p>


        </div>
    );
};

export default SimulationControl;
