import React, { useState, useEffect } from 'react';

const LogDisplay: React.FC = () => {
    const [logs, setLogs] = useState<any[]>([]);  //  logs is an array of objects
    const [error, setError] = useState<string>('');

    useEffect(() => {
        const fetchLogs = async () => {
            try {
                const response = await fetch('http://localhost:8081/api/logs');
                if (!response.ok) {
                    setError('Failed to fetch logs');
                }
                setError('');
                const data = await response.json();
                setLogs(data);  // Save logs to state
            } catch (error) {
                console.error(error);
            }
        };

        const intervalId = setInterval(fetchLogs, 2000);  // Fetch logs every 2 seconds
        return () => clearInterval(intervalId);  // Cleanup interval on component unmount
    }, []);

    return (
        <div>
            <h2>Logs Messages</h2>
            {error ? (
                <p className="errorTags">{error}</p>  // Display error if any
            ) : (
                <div className="log-container">
                    {logs.length > 0 ? (
                        <ul style={{ listStyleType: 'none', padding: 5 }}>
                            {logs.map((log) => (
                                <li key={log.id} className="log-item">
                                    {log.msg}
                                </li>  // Display the log message
                            ))}
                        </ul>
                    ) : (
                        <p>No logs available</p>  // Message if there are no logs
                    )}
                </div>
            )}
        </div>
    );
};

export default LogDisplay;
