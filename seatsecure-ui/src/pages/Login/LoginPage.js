import React from 'react';
import '../../global.css';
import './LoginPage.css';
import { useState } from 'react';
import {login} from'./Login.js';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';


const Login = () => {

    const[username,usernameupdate] = useState('');
    const[password,passwordupdate] = useState('');
    
    const ProceedLogin = (e) =>{
        e.preventDefault();

        if(validation()) {
            console.log('proceed');
        } 
    }
    const validation = ()=>{
        let result = true;
        if(username ==='' || username == null) {
            return false;
            TransformStream.warning("Please enter username");
        }
        if(password ==='' || password == null) {
            return false;
            TransformStream.warning("Please enter password");
        }
        return result;
    }
    const navigate = useNavigate();

    return (
        <div className="landing-container">

            <Navbar />

            <div className="landing-content">
                <div className="form-div">
                    <span className="material-symbols-outlined" style={{cursor:'pointer'}} onClick={() => navigate(-1)}>
                        keyboard_backspace
                    </span>
                    <form onSubmit = {ProceedLogin} className = "Container">

                        <h1>Welcome back!</h1>
                        <div className="input-icon">
                            <label>Email Address <span className = "errmsg" ></span></label>
                            < input value = {username} onChange ={e=>usernameupdate(e.target.value)} ></input>
                            <i className="fa fa-envelope icon"></i>
                        </div>

                        <div className="input-icon">
                            <label>Password <span className = "errmsg" ></span></label>
                            < input value = {password} onChange ={e=>passwordupdate(e.target.value)} ></input>
                            <i className="fa fa-eye icon"></i>
                        </div>
                        
                        <div className="input-icon">
                        <button type= "submit" className ="primary button" onClick>Login</button>
                        <Link></Link>
                        </div>

                    </form>
                    <div className="form-text"><Link to="/registration">I don't have an account? Sign Up</Link></div>
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

export default Login;