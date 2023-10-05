import React, { useEffect, useState } from 'react';
import '../../global.css';
import './EventsPage.css';
import Navbar from '../../components/Navbar';
import EventCard from '../../components/EventCard';


function EventsPage() {
    const [users, setUsers] = useState([]);

    const fetchUserData = () => {
        fetch("https://jsonplaceholder.typicode.com/users")
            .then(response => {
                return response.json()
            })
            .then(data => {
                console.log(data)
                setUsers(data)
            })
    }

    useEffect(() => {
        fetchUserData()
    }, [])

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
                    <div className='event-section-header'>
                        1 July 2023 - 31 July 2023
                    </div>
                    <div className='event-content'>
                        <EventCard />
                        <EventCard />
                        <EventCard />
                    </div>
                </section>
                <section>
                    <div className='event-section-header'>
                        1 July 2023 - 31 July 2023
                    </div>
                    <div className='event-content'>
                        <EventCard />
                        <EventCard />
                        <EventCard />
                        <EventCard />
                    </div>
                </section>
            </main>
        </div>
    );

}

export default EventsPage;