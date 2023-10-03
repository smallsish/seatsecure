import React from 'react';
import '../../global.css';
import './LandingPage.css';
import Navbar from '../../components/Navbar';

function LandingPage() {
    return (
        <div className="landing-container">
            <Navbar />

            <div className="landing-content">
                <h1 className="landing-h1">SEIZE THE MOMENT. SECURE YOUR SEATS.</h1>
            </div>

            <div className="landing-footer">
                <h1>FAISAL MANAP · YONO ONO · JJ LIN</h1>
            </div>
        </div>
    );

}

export default LandingPage;