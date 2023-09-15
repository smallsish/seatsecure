import React from 'react';
import '../../global.css';
import './RegistrationPage.css';
import Navbar from '../../components/Navbar';
import { Link } from 'react-router-dom';

function RegistrationPage() {
    return (
        <div className="landing-container">

        <Navbar/>

        <div className="landing-content">
            <div className="form-div">
                <span className="material-symbols-outlined">
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
                <div className="form-text"><Link to="/login">Have an existing account? Sign In</Link></div>
            </div>
        </div>
        <div className="landing-footer"style={{backgroundColor:'transparent'}} >
        </div>
    </div>

    );

}

export default RegistrationPage;