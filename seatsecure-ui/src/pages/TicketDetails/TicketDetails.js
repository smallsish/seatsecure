import React, { useEffect, useState } from 'react';
import '../../global.css';
import './TicketDetails.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import Faq from 'react-faq-component';
import axios from '../../api/axios';
import useUser from '../../hooks/useUser';
import useAuth from '../../hooks/useAuth';


const TicketDetails = () => {
    const user = useUser();
    var IDValue = parseInt(user.userID.ID);
    const auth = useAuth();
    const token = auth.auth.token;
    const [data, setData] = useState(null);

    useEffect(() => {
        makeTicketRequest()
    }, [])

    const makeTicketRequest = async () => {
        try {
            const response = await axios.get(`/api/v1/users/${IDValue}/tickets`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            const responseData = response.data;
            console.log(responseData)
            setData(responseData);
        }
        catch (error) {
            console.error('No User Page ', error);
        }
    };

    const populateTickets = () => {
        if (data && data.tickets) {
            const newTicketsData = data.tickets.map((ticket, i) => ({
                title: `Ticket ${ticket.id}`,
                content: `TICKET ID: ${ticket.id}, EVENT LOCATION: ${ticket.venue.venueName}, EVENT DATE: ${ticket.run.startDate}, PRICE: $${50 + i * 25}`,
            }));
            return newTicketsData;
        }
        return [];
    };

    const faqData = {
        rows: populateTickets(),
    };

    return (

        <div id='login-container' className="ticketdetails-container">
            <Navbar />
            <div className="landing-content">
                <div className="ticket-div">

                    <div className="event-div-text">Ticket Details</div>
                    <div className="event-divider-line"></div>

                    <div className="event-collapsable">
                        <Faq data={faqData}
                            styles={{
                                type: CanvasGradient,
                                bgColor: "Transparent",
                                rowContentTextSize: "Large",
                                titleTextColor: "White",
                                rowTitleTextSize: 'Large',
                                rowTitleColor: "White",
                                rowContentColor: "White",
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