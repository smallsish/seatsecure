import React, { useState } from 'react';
import './Navbar.css';
import { Link, useLocation } from 'react-router-dom';
import logo from '../images/Logo.svg';
import useAuth from '../hooks/useAuth';

function Navbar() {
  

  const location = useLocation();
  const isLoginPage = location.pathname === '/login';
  
  const displayMenu = () => {
    var nav = document.querySelector('.nav');
    // Toggle the 'hidden' class on the nav element to hide/show it
    nav.classList.toggle('hidden');
    console.log("Menu Clicked");
  };

  return (
    <header className="header">
      <Link to="/"><img src={logo} alt="Logo" /></Link>
      <nav className={`nav ${isLoginPage ? 'hidden' : ''}`}>
        <ul className="nav-list" id="nav-list">
          <li className="nav-item"><Link to="/events">Events</Link></li>
          <li className="nav-item"><a>FAQ</a></li>
          <li className="nav-item"><a><span className="material-symbols-outlined">search</span></a></li>
          {!isLoginPage && (
            <li id="login-btn" className="nav-item"><Link to="/login">Login</Link></li>
          )}
        </ul>
      </nav>
      <span id="menu-btn" className="material-symbols-outlined" onClick={displayMenu}>menu</span>
    </header>
  );
}

export default Navbar;