import React from 'react';
import '../../global.css';
import './RegistrationPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';

function RegistrationPage() {

        // For back button
        const navigate = useNavigate();

    return (
        <div className="landing-container">

        <Navbar/>

        <div className="landing-content">
            <div className="form-div" style={{paddingBottom:'0px'}}>
                <span className="material-symbols-outlined" style={{cursor:'pointer'}} onClick={() => navigate(-1)}>
                    keyboard_backspace
                </span>
                <form>
                    <h1>Let's get you started!</h1>
                    <div className="registration-input-icon">
                        <input type="text" name="" id="" placeholder="Name"/>
                    </div>
                    <div className="registration-input-icon">
                        <input type="number" name="" id="" placeholder="Phone Number"/>
                    </div>
                    <div className="registration-input-icon">
                        <input type="email" name="" id="" placeholder="Email"/>
                    </div>
                    <div className="registration-input-icon">
                        <input type="password" name="" id="" placeholder="Password"/>
                    </div>
                    <div className="registration-input-icon"><input type="submit" value="Sign Up"/></div>

                </form>
                <div className="form-text"><Link to="/login">Have an existing account? <span style={{color:'#F4C430'}}>Sign In</span></Link></div>
            </div>
        </div>
        <div className="registration-footer">
        </div>
    </div>

    );

}

export default RegistrationPage;