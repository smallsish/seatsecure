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
                    <h1>Welcome back!</h1>
                    <div className="input-icon">
                        <input type="email" name="" id="" placeholder="Enter your email"/>
                        <i className="fa fa-envelope icon"></i>
                    </div>
                    <div className="input-icon">
                        <input type="password" name="" id="" placeholder="Enter your password"/>
                        <i className="fa fa-eye icon"></i>
                    </div>
                    <div className="input-icon"><input type="submit" value="Sign In"/></div>

                </form>
                <div className="form-text">I don't have an account? Sign Up</div>
                <div className="form-divider">
                    <div className="form-divider-line"></div>
                    <div> OR </div>
                    <div className="form-divider-line"></div>
                </div>
                <div className="form-text">Facebook | Google</div>
            </div>
        </div>
        <div className="landing-footer"style={{backgroundColor:'transparent'}} >
        </div>
    </div>

    );

}

export default RegistrationPage;