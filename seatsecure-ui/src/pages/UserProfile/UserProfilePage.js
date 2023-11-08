import React, { useEffect, useState } from 'react';
import '../../global.css';
import './UserPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import axios from '../../api/axios';
import useUser from '../../hooks/useUser';
import useAuth from '../../hooks/useAuth';

const UserProfilePage = () => {
    const user = useUser();
    var IDValue = parseInt(user.userID.ID);
    const auth = useAuth();
    const token = auth.auth.token;
    const [data, setData] = useState(null);

    useEffect(() => {
        makeUserRequest()
    }, [])
    const makeUserRequest = async () => {
        try {
            const response = await axios.get(`/api/v1/users/${IDValue}`, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            const responseData = response.data;
            setData(responseData);
        }
        catch (error) {
            console.error('No User Page ', error);
        }
    };

    return (
        <div className="user-container">
            <Navbar />
            <div className="landing-content">
                <div className="user-div"><div className="event-div-text">User Profile</div>
                <div className="event-divider-line"></div>
                    <div className='user-wrapper'>
                        <div className="user-picture"></div>
                        <div className="text-container">
                        <div className="User-div-text">
                            Username:<span className="custom-font"> {data ? data.user.username : 'Loading'} </span>
                        </div>
                        <div className="User-div-text">
                            ID:<span className="custom-font"> {data ? data.user.id : 'Loading'}  </span>
                        </div>
                        <div className="User-div-text">
                            FirstName:<span className="custom-font"> {data ? data.firstName : 'Loading'}  </span>
                        </div>
                        <div className="User-div-text">
                            LastName:<span className="custom-font"> {data ? data.lastName : 'Loading'} </span>
                        </div>
                        <div className="User-div-text">
                            Email:<span className="custom-font"> {data ? data.email : 'Loading'} </span>
                        </div>
                        <div className="User-div-text">
                            Gender:<span className="custom-font"> {data ? data.gender : 'Loading'} </span>
                        </div>
                        <div className="User-div-text">
                            Phone Number:<span className="custom-font"> {data ? data.phoneNumber : 'Loading'} </span>
                        </div>
                        </div>
                        {data ? (
                            <Link to="/TicketDetails">
                                <div className="ticket-button">
                                    <input className="ticket-button-input" type="button" value="View Tickets" />
                                </div>
                            </Link>)
                            : null}
                    </div>
                </div>
            </div>
            <div className="landing-footer" style={{ backgroundColor: 'transparent' }} >
            </div>
        </div>

    );

}
export default UserProfilePage;