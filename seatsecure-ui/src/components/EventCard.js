import React from 'react';
import './EventCard.css';
import { Link, useNavigate } from 'react-router-dom';
import EventDetailsPage from './../pages/EventDetails/EventDetailsPage';


function EventCard() {

  return (
    <div className='event-card-container'>
        <div className='event-card-header'>
            <h1 className='event-card-title'>Coldplay Concert</h1>
            <h2 className='event-card-date'>2 July 2023 - 6 July 2023</h2>
        </div>  
        <div className='find-out-more-div'>

            <button className='event-card-button'>
            <Link to="/EventDetails" className='event-card-button'>
            <span className='find-out-more-text'>FIND OUT MORE</span>
             <i className="gg-arrow-top-right"></i>
              </Link>
            </button>
        </div>
    </div>
  );
}

export default EventCard;