import React, { useEffect, useState } from 'react';
import '../../global.css';
import './EventsDetails.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import Faq from 'react-faq-component';
import useUser from '../../hooks/useUser';


const EventDetailsPage = (props) => {

  const navigate = useNavigate();
  const user = useUser();
  var IDValue = parseInt(user.userID.ID);

  const location = useLocation();
  const state = location.state;
  console.log(state);
  
  const title = "SAVE-THE-DAY CONCERT BY COLDPLAY 2023"
  const description = "Following the spectacular sellos newly announced January and February 2024 dates in Asia, the band have today announced additional dates as part of their record-breaking Music Of The Spheres World Tour , Already making history for announcing an unprecedented four-show run at Singapore’s largest venue, Coldplay will now be the first act ever to play five nights at Singapore’s National Stadium. The band also broke Singapore’s record for most tickets sold by an artist in a single day, surpassing 200,000.";

  const data = {
    title: "",
    rows: [
      {
        title: "Our prices",
        content: "Lorem ipsum dolor sit amet, consectetur "
      },
      {
        title: "Exchange and refund policy",
        content: "No refunds will be given until the results of the raffles are decided upon."
      },
      {
        title: "Admission Policy",
        content: "Our admission policy involves a multiple raffles."
      }
    ]
  }

  const handlepurchase = () => {

    console.log(IDValue);

    if( isNaN( IDValue)) {
      alert('Please login to continue!');
    }
    else {

      navigate(
        '/CatSelection',
        {state:state}
    
      ); //state = {Event}
    }
  };

  return (

    <div id='login-container' className="eventdetails-container">
      <Navbar />
      <div className="landing-content">
        <div className="event-div">
          <div className="event-div-title">{state.event.eventName}
          </div>
          <div className="event-picture">
            <div className="event-date">Date:{state.event.startDate} to {state.event.endDate}</div>
            <div className="event-location">Location:{state.venue.venueName}</div>
          </div>
          <div className="event-button">
            <input type="button" onClick ={handlepurchase} value="Purchase" />
          </div>
          <div className="event-div-text">Event Details</div>
          <div className="event-divider-line"></div>
          <div className="event-text-details">{description}</div>
          <div className="event-collapsable">
            <Faq data={data}
              styles={{
                type: CanvasGradient,
                bgColor: "Transparent",
                rowTitleTextSize: "Small",
                titleTextColor: "White",
                rowTitleTextSize: 'Medium',
                rowTitleColor: "White",
                rowContentColor: "White",
                rowContentTextSize: '16px',
                rowContentPaddingTop: '5px',
                rowContentPaddingBottom: '10px',
                rowContentPaddingLeft: '50px',
                rowContentPaddingRight: '150px',
                arrowColor: "White",
              }} />
          </div>
        </div>
      </div>
      <div className="landing-footer" style={{ backgroundColor: 'transparent' }} >
      </div>
    </div>

  );

}
export default EventDetailsPage;