import React, { useState, useEffect } from 'react';
import './Navbar.css';
import { Link, useLocation } from 'react-router-dom';
import logo from '../images/Logo.svg';
import useAuth from '../hooks/useAuth';
import axios from '../api/axios';
import useUser from '../hooks/useUser';

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

  useEffect(() => {
    if (isLoggedIn) {
      makeAuthenticatedRequest();
    }
  }, [location.pathname]);

  const makeAuthenticatedRequest = async () => {
    try {
      const response = await axios.get(`/api/v1/users/${IDValue}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });

      const responseData = response.data;
      console.log('Authenticated Request Response:', responseData);
      setData(responseData);
    } catch (error) {
      console.error('Authenticated Request Error:', error);
      // Handle the error as needed.
    }
  };

  const renderNavbarContent = () => {
    if (isLoggedIn && data) {
      return (
        <Link to="/UserProfile">
          <span className='nav-username'>{data.user.username}</span>
        </Link>
      );
    } else if (isLoginPage) {
      return null;
    } else {
      return (
        <Link to="/login">Login</Link>
      );
    }
  };

  return (
    <header className="header">
      <Link to="/"><img src={logo} alt="Logo" /></Link>
      <nav className={`nav ${isLoginPage ? 'hidden' : ''}`}>
        <ul className="nav-list" id="nav-list">
          <li className="nav-item"><Link to="/events">Events</Link></li>
          <li className="nav-item"><a>FAQ</a></li>
          <li className="nav-item"><a><span className="material-symbols-outlined">search</span></a></li>
          <li id="login-btn" className="nav-item">
            {renderNavbarContent()}
          </li>
        </ul>
      </nav>
      <span id="menu-btn" className="material-symbols-outlined" onClick={displayMenu}>menu</span>
    </header>
  );
}

export default Navbar;
