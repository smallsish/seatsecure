import React, { useEffect, useState } from 'react';
import '../../global.css';
import './TicketDetails.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate , useLocation} from 'react-router-dom';
import Faq from 'react-faq-component';



const TicketDetails = ()  =>{

    const ticketsData = [
        {
        title: "Ticket 1",
        content: "TICKET ID: 12345, EVENT LOCATION: Venue 1, EVENT DATE: 01/01/2023, PRICE: $50",
        },
        {
        title: "Ticket 2",
        content: "TICKET ID: 54321, EVENT LOCATION: Venue 2, EVENT DATE: 02/15/2023, PRICE: $75",
        },
    // Add more ticket objects as needed
         ];

     const data = {
            rows: [
            ]
     }

     for (const ticket of ticketsData) {
        data.rows.push({
          title: ticket.title,
          content: ticket.content
        });
      }

    return (
  
        <div id='login-container' className="ticketdetails-container">
            <Navbar />
            <div className="landing-content">
                <div className="ticket-div">
                    
                        <div className ="event-div-text">Ticket Details</div>
                        <div className="event-divider-line"></div>
                        
                        <div className = "event-collapsable">  
                        <Faq data={data}
                        styles={{
                            type:CanvasGradient,
                            bgColor: "Transparent",
                            rowContentTextSize:"Large",
                            titleTextColor: "White",
                            rowTitleTextSize: 'Large',
                            rowTitleColor: "White",
                            rowContentColor:"White",
                            rowContentTextSize: '14px',
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
export default TicketDetails;