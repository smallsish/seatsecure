import React from 'react';
import '../../global.css';
import './LoginPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';

function LoginPage() {
    // For back button
    const navigate = useNavigate();
    
    return (
        <div id='login-container' className="landing-container">

            <Navbar />

            <div className="landing-content">
                <div className="form-div">
                    <span className="material-symbols-outlined" style={{cursor:'pointer'}} onClick={() => navigate(-1)}>
                        keyboard_backspace
                    </span>
                    <form>
                        <h1>Welcome back!</h1>
                        <div className="input-icon">
                            <input type="email" name="" id="" placeholder="Email" />
                            <i className="fa fa-envelope icon"></i>
                        </div>
                        <div className="input-icon">
                            <input type="password" name="" id="" placeholder="Password" />
                            <i className="fa fa-eye icon"></i>
                        </div>
                        <div className="input-icon"><input type="submit" value="Sign In" /></div>

                    </form>
                    <div className="form-text"><Link to="/registration">I don't have an account? <span style={{color:'#F4C430'}}>Sign Up</span></Link></div>
                    <div className="form-divider">
                        <div className="form-divider-line"></div>
                        <div> OR </div>
                        <div className="form-divider-line"></div>
                    </div>
                    <div className="form-text">Facebook | Google</div>
                </div>
            </div>
            <div className="landing-footer" style={{ backgroundColor: 'transparent' }} >
            </div>
        </div>

    );

}

export default LoginPage;