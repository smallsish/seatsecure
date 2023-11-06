import React, { useState, useEffect } from 'react';
import './Navbar.css';
import { Link, useLocation } from 'react-router-dom';
import logo from '../images/Logo.svg';
import useAuth from '../hooks/useAuth';
import axios from '../api/axios';
import useUser from '../hooks/useUser';
import UserProfilePage from '../pages/UserProfile/UserProfilePage';



function Navbar() {
  const auth = useAuth();
  const user = useUser();
  var IDValue = parseInt(user.userID.ID);
  const token = auth.auth.token;
  const isLoggedIn = auth.auth.isLoggedIn;
  const [data, setData] = useState(null);
  const location = useLocation();
  const isLoginPage = location.pathname === '/login';






  const displayMenu = () => {
    var nav = document.querySelector('.nav');
    nav.classList.toggle('hidden');
    console.log("Menu Clicked");
  };

  const showData = () => {
    console.log(data[0].username);
    console.log(user.userID.ID);
  };

  useEffect(() => {
    if (isLoggedIn) {
      makeAuthenticatedRequest();
    }

  }, [location.pathname]);



  const makeAuthenticatedRequest = async () => {
    try {
      // Use the obtained token in the Authorization header for your requests.
      const response = await axios.get('/api/v1/users', {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      // Axios automatically parses JSON responses, so you can access the response data directly.
      const responseData = response.data;

      // Handle the response data as needed.
      // console.log('Authenticated Request Response:', responseData[0].username);
      setData(responseData);
    } catch (error) {
      console.error('Authenticated Request Error:', error);
      // Handle the error as needed.
    }
  };

  return (
    <header className="header">
      <Link to="/"><img src={logo} alt="Logo" /></Link>
      <nav className={`nav ${isLoginPage ? 'hidden' : ''}`}>
        <ul className="nav-list" id="nav-list">
          <li className="nav-item"><Link to="/events">Events</Link></li>
          <li className="nav-item" onClick={showData}><a>FAQ</a></li>
          <li className="nav-item"><a><span className="material-symbols-outlined">search</span></a></li>
          {isLoginPage ? null : isLoggedIn ? (
            <li id="login-btn" className="nav-item">
              {data && data.length > 0 ? (
                <Link to="/UserProfile">
                  <span className='nav-username'>{data[IDValue - 1].username}</span>
                </Link>
              ) : (

                <span className='nav-username'> </span>

              )}
            </li>
          ) : (
            <li id="login-btn" className="nav-item">
              <Link to="/login">Login</Link>
            </li>
          )}
        </ul>
      </nav>
      <span id="menu-btn" className="material-symbols-outlined" onClick={displayMenu}>menu</span>
    </header>
  );
}

export default Navbar;