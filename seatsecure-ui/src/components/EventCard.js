import React from 'react';
import './EventCard.css';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import EventDetailsPage from './../pages/EventDetails/EventDetailsPage';

function EventCard({Event}) {
  const location =useLocation();
  const data = location.state;

  return (
    <div className='event-card-container'>
        <div className='event-card-header'>
            <h1 className='event-card-title'>{Event.event.eventName}</h1>
            <h2 className='event-card-date'>{Event.event.startDate} - {Event.event.endDate}</h2>
        </div>  
        <div className='find-out-more-div'> 

            <button className='event-card-button'>
            <Link to= '/EventDetails' state = {Event} className='event-card-button'>
            <span className='find-out-more-text'>FIND OUT MORE</span>
             <i className="gg-arrow-top-right"></i>
              </Link>
            </button>
        </div>
    </div>
  );
}

export default EventCard;