import React, { useEffect, useState } from 'react';
import '../../global.css';
import './EventsPage.css';
import Navbar from '../../components/Navbar';
import EventCard from '../../components/EventCard';
import axios from '../../api/axios';
import useAuth from '../../hooks/useAuth';

function EventsPage() {
    const [users, setUsers] = useState([]);
    const [data, setData] = useState(null);
    const auth = useAuth();
    const token = auth.auth.token;

    useEffect(() => {
        makeEventRequest()
    }, [])

    const makeEventRequest = async () => {
        try {

            const response = await axios.get(`/api/v1/events`, {
            });

            const responseData = response.data;
            setData(responseData);
        }
        catch (error) {
            console.error('No Events page ', error);
        }
    };

    useEffect(() => {
        console.log(data); // This is executed after data has been updated
    }, [data]);

    const showData = () => {
        console.log(data[0]);
    }

    const renderEventCards = () => {
        if (data) {
            return data.map((event, index) => (
                <EventCard
                    key={index} // Use a unique key for each card (index is used here for simplicity)
                    Event={event}
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
                        Current Events Available
                    </div>
                    <div className='event-content'>
                        {renderEventCards()}
                    </div>
                </section>
            </main>
        </div>
    );
}

export default EventsPage;