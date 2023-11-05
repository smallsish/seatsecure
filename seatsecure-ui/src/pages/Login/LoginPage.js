import { React, useRef, useState, useEffect, useContext } from 'react';
import '../../global.css';
import './LoginPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../../context/AuthProvider';
import axios from '../../api/axios';
import UserContext from '../../context/UserIDProvider';


const LOGIN_URL = '/api/v1/auth/authenticate'

function LoginPage() {
    const { setAuth } = useContext(AuthContext);
    const navigate = useNavigate();
    const userRef = useRef();
    const errRef = useRef();
    const [user, setUser] = useState('');
    const [pwd, setPwd] = useState('');
    const [errMsg, setErrMsg] = useState('');
    const [success, setSuccess] = useState(false);
    const { setUserID } = useContext(UserContext);

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        setErrMsg('');
    }, [user, pwd])

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(LOGIN_URL,
                JSON.stringify({ username: user, password: pwd }),
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }


            )
            ;
            // console.log(JSON.stringify(response?.data));
            
            console.log(response);
            const token = response?.data?.token;
            const ID = response?.data?.id;
            const isLoggedIn = true;
            console.log(ID);
            setUserID({ID});
            setAuth({ token, isLoggedIn });;
            setUser('');
            setPwd('');
            setSuccess(true);
            navigate('/');
            
        } catch (err) {
            if (!err?.response) {
                setErrMsg('No Server Response');
            } else if (err.response?.status === 400) {
                setErrMsg('Missing Username or Password');
            } else if (err.response?.status === 401) {
                setErrMsg('Unauthorized');
            } else {
                setErrMsg('Login Failed');
            }
        }
    }

    return (
        <div id='login-container' className="login-container">
            <Navbar />
            <div className="landing-content">
                <div className="form-div">
                    <span className="material-symbols-outlined" style={{ cursor: 'pointer' }} onClick={() => navigate(-1)}>
                        keyboard_backspace
                    </span>
                    <form onSubmit={handleSubmit}>
                        <h1>Welcome back!</h1>
                        <div className="input-icon">
                            <input
                                type="text"
                                id="username"
                                ref={userRef}
                               placeholder="Username"
                                autoComplete='off'
                                onChange={(e) => setUser(e.target.value)}
                                value={user}
                                required
                            />
                            <i className="fa fa-envelope icon"></i>
                        </div>
                        <div className="input-icon">
                            <input
                                type="password"
                                id="password"
                                placeholder="Password"
                                onChange={(e) => setPwd(e.target.value)}
                                value={pwd}
                                required
                            />
                            <i className="fa fa-eye icon"></i>
                        </div>
                        <div className="input-icon"><input type="submit" value="Sign In" /></div>

                    </form>
                    <div className="form-text"><Link to="/registration">I don't have an account? <span style={{ color: '#F4C430' }}>Sign Up</span></Link></div>
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