import React, { useEffect, useState } from 'react';
import '../../global.css';
import './UserPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate , useLocation} from 'react-router-dom';
import axios from '../../api/axios';
import useUser from '../../hooks/useUser';
// import { Button, Card, CardBody, CardFooter, Col, Container, Input, Row, Table } from 'reactstrap';



const UserProfilePage = ()  =>{

    const user = useUser();
    var IDValue = parseInt(user.userID.ID);

    const [data, setData] = useState(null);

    useEffect(() => {
        makeUserRequest()
    }, [])
    const makeUserRequest = async () => {

        try {
            console.log(IDValue);
            const response = await axios.get(`http://localhost:8080/api/v1/users/${IDValue}`,
                JSON.stringify({}),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );

            const responseData = response.data;
            setData(responseData);
            console.log(responseData);

        }
        catch (error) {
            console.error('No User Page ', error);
        }
    };

    return (
        <div className="user-container">
            <Navbar />
            <div className="landing-content">
                <div className="user-div">
                        <div>

            <div className ="event-div-text">User Profile</div>
            <div className="event-divider-line"></div> 
                   

            <div className = "user-picture"></div>


            <div className ="User-div-text">
            Username:<span className="custom-font">{data? data.username:'Loading'} </span>
            </div>
            <div className ="User-div-text">
            ID:<span className="custom-font"> {data? data.id:'Loading'}  </span>
            </div>
            <div className ="User-div-text">
            FirstName:<span className="custom-font"> {data? data.firstName:'Loading'}  </span>
            </div>
            <div className ="User-div-text">
            LastName:<span className="custom-font"> {data? data.lastName:'Loading'} </span>
            </div>
            <div className ="User-div-text">
            Email:<span className="custom-font"> {data?data.email:'Loading'} </span>
            </div>
            <div className ="User-div-text">
            Gender:<span className="custom-font"> {data? data.gender :'Loading'} </span>
            </div>
            <div className ="User-div-text">
            Phone Number:<span className="custom-font"> {data? data.phoneNumber: 'Loading'} </span>
            </div>
            <div className ="User-div-text">
            Role:<span className="custom-font"> {data? data.role: 'Loading'} </span>
            </div>
            
            {data? (
            <Link to="/TicketDetails">
            <div className="ticket-button">  
                        <input className="ticket-button-input" type="button" value="View Tickets" />
                        </div>
                       </Link> )   
            :null }

                

        </div>
        </div>
            </div>
            <div className="landing-footer" style={{ backgroundColor: 'transparent' }} >
            </div>
            </div>
    
    );

}
export default UserProfilePage;