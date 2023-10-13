import React, { useEffect, useState } from 'react';
import '../../global.css';
import './EventsPage.css';
import Navbar from '../../components/Navbar';
import EventCard from '../../components/EventCard';
import axios from '../../api/axios';



function EventsPage() {
    const [users, setUsers] = useState([]);
    const [data, setData] = useState(null);

    useEffect(() => {
        makeEventRequest()
    }, [])
    const makeEventRequest = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/v1/events",
                JSON.stringify({}),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: false
                }
            );

            const responseData = response.data;
            setData(responseData);
            console.log(responseData);

        }
        catch (error) {
            console.error('No Events page ', error);
        }

    };

    
    const showData = () => {
        console.log(data[0].eventName);
    }
    const renderEventCards = () => {
        if(data) {
        return data.map((event, index) => (
            <EventCard
                key={index} // Use a unique key for each card (index is used here for simplicity)
                Event = {event}
            />
        ));
        }
        return null;
    };

    return (
        <div className='event-container'>
            <Navbar />
            <div className='event-page-header'>
                {/* <div className='date-selector'>
                    <i className="fa fa-calendar" aria-hidden="true"></i>
                </div> */}
            </div>
            <main>
                <section>

                    <div className='event-section-header' onClick={showData}>
                        1 July 2023 - 31 July 2023
                    </div>  
                    <div className='event-content'>
                        
                    {renderEventCards()}

                    </div>
                </section>
                <section>
                    <div className='event-section-header'>
                        1 July 2023 - 31 July 2023
                    </div>
                    <div className='event-content'>
                      
                    </div>
                </section>
            </main>
        </div>
    );

}

export default EventsPage;