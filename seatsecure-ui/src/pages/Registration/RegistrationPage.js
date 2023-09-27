import React, { useState, useContext } from 'react';
import '../../global.css';
import './RegistrationPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../../context/AuthProvider';
import axios from '../../api/axios';

const REGISTER_URL = '/api/v1/auth/register-user';

function RegistrationPage() {
    const { setAuth } = useContext(AuthContext);
    const navigate = useNavigate();
    const [errMsg, setErrMsg] = useState('');
    const [rUsername, setUsername] = useState('');
    const [rPassword, setPassword] = useState('');
    const [rFName, setFName] = useState('');
    const [rLName, setLName] = useState('');
    const [rGender, setGender] = useState('');
    const [rEmail, setEmail] = useState('');
    const [rPhone, setPhone] = useState('');

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(REGISTER_URL,
                JSON.stringify({
                    firstName: rFName,
                    lastName: rLName,
                    username: rUsername,
                    email: rEmail,
                    password: rPassword,
                    gender: rGender,
                    phoneNumber: rPhone
                }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
                // .then()
                // .catch(err => {setErrMsg(err.toString())})
        // console.log(JSON.stringify(response?.data));
        // const token = response?.data?.token;
        // const isLoggedIn = true;
        // Commented out authentication because user level auth is not capable of retrieving user info
        // setAuth({ token, isLoggedIn });
            // console.log(errMsg);
            navigate('/login');
            alert("Successfully registered, please log in.");
        } catch (err) {
            setErrMsg(err.response.data.message);
            //console.log(err);
        }
    }

    const loadDummy = () => {
        setFName("John");
        setLName("Doe");
        setGender("MALE");
        setPhone("1234567890");
        setEmail("testEmail@gmail.com");
        setUsername("john_doe");
        setPassword("password");
      };
      

    return (
        <div className="registration-container">
            <Navbar />
            <div className="landing-content">
                <div className="form-div" style={{ paddingBottom: '0px' }}>
                    <span className="material-symbols-outlined" style={{ cursor: 'pointer' }} onClick={() => navigate(-1)}>
                        keyboard_backspace
                    </span>
                    <form onSubmit={handleRegister}>
                        <h1 onClick={loadDummy}>Let's get you started!</h1>
                        <div className="registration-input-icon">
                            <input type="text" name="" value={rFName} id="register-first-name" onChange={(e) => setFName(e.target.value)} placeholder="First Name" />
                        </div>
                        <div className="registration-input-icon">
                            <input type="text" name="" value={rLName} id="register-last-name" onChange={(e) => setLName(e.target.value)} placeholder="Last Name" />
                        </div>
                        <div className="registration-input-icon">
                            <div className="gender-structure">
                                <div>Gender:</div>
                                <div className="radio-structure">
                                    <input type="radio" name="gender" value="MALE" onChange={(e) => setGender(e.target.value)}/>
                                    <label>Male</label>
                                </div>
                                <div className="radio-structure">
                                    <input type="radio" name="gender" value="FEMALE" onChange={(e) => setGender(e.target.value)}/>
                                    <label>Female</label>
                                </div>
                            </div>
                            {/* <input type="text" name="" value={rGender} id="register-gender" onChange={(e) => setGender(e.target.value)} placeholder="Gender" /> */}
                        </div>
                        <div className="registration-input-icon">
                            <input type="tel" name="" value={rPhone} id="register-phone" onChange={(e) => setPhone(e.target.value)} placeholder="Phone Number" />
                        </div>
                        <div className="registration-input-icon">
                            <input type="text" name="" value={rEmail} id="register-email" onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
                        </div>
                        <div className="registration-input-icon">
                            <input type="text" name="" value={rUsername} id="register-username" onChange={(e) => setUsername(e.target.value)} placeholder="Username" />
                        </div>
                        <div className="registration-input-icon">
                            <input type="password" value={rPassword} name="" id="register-password" onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
                        </div>
                        <div className="registration-input-icon submit" id='register-submit'><input type="submit" value="Sign Up" /></div>
                        {/* <div className="error">{errMsg}</div> */}
                        { errMsg && <div className="error"> { errMsg } </div> }
                    </form>
                    <div className="form-text"><Link to="/login">Have an existing account? <span style={{ color: '#F4C430' }}>Sign In</span></Link></div>
                </div>
            </div>
            <div className="registration-footer">
            </div>
        </div>

    );

}

export default RegistrationPage;