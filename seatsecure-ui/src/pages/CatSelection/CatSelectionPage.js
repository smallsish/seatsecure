import { React, useRef, useState, useEffect, useContext } from 'react';
import '../../global.css';
import './CatSelectionPage.css';
import Navbar from '../../components/Navbar';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../../context/AuthProvider';
import axios from '../../api/axios';
import Select from 'react-select';
import venueImage from './venue.png';

// To be replaced with DB data
const options = [
    { value: 'SUNDAY, 24TH SEPTEMBER 2023', label: 'SUNDAY, 24TH SEPTEMBER 2023' },
    { value: 'SATURDAY, 23RD SEPTEMBER 2023', label: 'SATURDAY, 23RD SEPTEMBER 2023' }
];

const options_quantity = [
    { value: '1', label: '1' },
    { value: '2', label: '2' },
    { value: '3', label: '3' }
]

const areas = [
    { title:"L-GOLD", coords:"98,143,142,165,135,208,143,242,162,269,197,297,178,337,123,297,95,256,88,197", shape:"poly"},
    { title:"R-GOLD", coords:"356,161,363,198,357,235,340,264,301,295,320,337,369,305,395,268,408,229,411,169,399,141", shape:"poly"},
    { title:"L-BRONZE", coords:"71,170,71,216,76,249,95,288,57,309,39,280,31,233,31,174,29,161,54,165", shape:"poly"},
    { title:"R-BRONZE", coords:"427,170,428,217,419,251,408,286,443,310,463,264,473,220,469,166", shape:"poly"},
    { title:"PLATINUM", coords:"104,304,141,336,191,364,229,374,285,373,330,357,371,330,395,306,427,329,396,360,366,383,320,405,266,414,191,405,137,390,88,353,68,329", shape:"poly"},
    { title:"VIP-2", coords:"212,300,193,345,237,359,271,355,304,345,286,301,249,308", shape:"poly"},
    { title:"VIP-1", coords:"171,153,159,177,216,179,339,178,335,153", shape:"poly"}
]

function CatSelectionPage() {

    const [selectedArea, setSelectedArea] = useState('');
    const [selectedQuantity, setSelectedQuantity] = useState(1); 
    const [selectedDate, setSelectedDate] = useState(options[0]);

    const handleAreaClick = (title) => {
        console.log(`Area ${title} clicked`);
        setSelectedArea(title);
    }

    const handleCheckout = () => {
        console.log(`Selected Area: ${selectedArea}`);
        console.log(`Selected Quantity: ${selectedQuantity}`);
        console.log(`Selected Date: ${selectedDate.value}`);
    }

    return (
        <div id='cat-selection-container' className="cat-selection-container">
            <Navbar />
            <div className="landing-content">
                <div className="cat-selection-div">
                    <div className='cat-selection-wrapper'>
                        <div className='cat-selection-header'>
                            <span>SAVE.THE.DAY CONCERT BY COLDPLAY 2023</span>
                            <div className='cat-selection-date-venue'>
                                <div>Date: 21st Sep - 24th Sep</div>
                                <div>Location: Singapore Indoor Stadium</div>
                            </div>
                        </div>

                        <div className='cat-selection-select-date'>
                            <div className='cat-selection-select-date-text'>
                                SELECT DATE
                            </div>
                            <div>
                                <Select
                                    defaultValue={options[0]}
                                    options={options}
                                    styles={{
                                        control: (provided, state) => ({
                                            ...provided,
                                            borderRadius: '0',
                                            padding: '5px 0px',
                                            backgroundColor: 'transparent', 
                                            borderColor: state.isFocused ? 'blue' : 'gray', 
                                            boxShadow: state.isFocused ? '0 0 5px rgba(0, 0, 255, 0.5)' : 'none', 
                                            color: 'black',
                                        }),
                                        singleValue: (provided) => ({
                                            ...provided,
                                            color: 'white', 
                                            fontFamily: 'Inter',
                                        }),
                                        menu: (provided) => ({
                                            ...provided,
                                            borderRadius: '0',
                                            backgroundColor: 'black', 
                                            fontFamily: 'Inter',
                                        })
                                    }}
                                />
                            </div>
                        </div>

                        <div className='cat-selection-venue-layout'>
                            <img src={venueImage} width={'500px'} useMap='#image-map'/>

                            <map name="image-map">
                                {areas.map((area, index) => (
                                    <area
                                        key={index}
                                        shape={area.shape}
                                        coords={area.coords}
                                        onClick={() => handleAreaClick(area.title)}
                                        className="area-outline"
                                    />
                                ))}
                            </map>
                        </div>

                        <div className='cat-selection-options'>
                            <div className='cat-selection-options-cat'>CAT SELECTED: {selectedArea}</div>
                            <div className='cat-selection-options-quantity'>
                                <span>TICKET QUANTITY: </span>
                                <input type="number" className="cat-selection-options-quantity-input" defaultValue={1} min="1" max="3" size="1" />
                            </div>
                        </div>

                        <div className='cat-selection-checkout'>
                            <button className='checkout-button' onClick={handleCheckout}>
                                CHECKOUT
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="landing-footer" style={{ backgroundColor: 'transparent' }} >
            </div>
        </div>
    );

}

export default CatSelectionPage;