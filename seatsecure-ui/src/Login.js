import {useRef, useState , useEffect} from 'react';
import {useHistory} from 'react-router-dom';


//import hooks from react library
//useRef -> hook to create mutable references to Dom element, to store values that don't trigger re-renders. Example: 
//useEffect -> side effect for functional components -> fetch data from api backend
//UseState -> manage state variables , re-render component to update state. example:press button and update count 



const Login = () => {

    const userRef = useRef();
    const errRef = useRef();
    //const history = useHistory();

    const[email,setEmail] = useState('');
    const[pwd,setPwd] = useState('');
    const[errMsg,setErrMsg] = useState('');
    const[success,SetSuccess] = useState( );
    // initial values of each variables are empty
    // they will respond to message.
    useEffect(() => {
        userRef.current.focus();
    } , [] ) // nothing in [] , only happen when component loads
    // focus  on the user input 
    useEffect(() => {
        setErrMsg('');
    } , [email,pwd] )


   /* useEffect(() =>   {
        if(localStorage.getItem('')){ // to get something
            history.push("/add")
        }

    }) */

  
    // will run when u make changes to user or pwd, used to reset error message
    const handleSubmit = async (e) => { // an asynchronous function
        e.preventDefault();
        console.log(email,pwd);
        let item= {email,pwd};

        let result = await fetch("http://localhost:8080/", {// put ur endpoint here
            method: "POST",
            headers:{
                "Content-type":"application/json",
                "Accept":'application/json'
            },
            body: JSON.stringify(item)
        });

        result = await result.json();
        localStorage.setItem("user-info",JSON.stringify(result))

        history.push("/add")

        setEmail(''); // instantly clear components after login submit
        setPwd('');
        SetSuccess(true);
     // authenticate true when user pwd is entered.
    }



    return (// if success go to login page.

        <> { success? (
            <section>
                <h1>You are logged in!!!</h1>   
                <br/>
                <p>
                    <a href = "#">Go to Home</a>
                    <button >Go Back</button>
                </p>
            </section>
        )
        :(<section> // section element
            <p ref = {errRef} className = {errMsg?"errmsg":"offscreen"} 
            aria-live = "assertive"></p> 
        <h1>Sign In </h1> 
        <form onSubmit ={handleSubmit}> 
        <label htmlFor ="email">Email:</label>
        <input 
                type = "email" 
                id = "email"
                ref={userRef}
                autoComplete ="off" // remove past entries
                onChange = {(e) => setEmail(e.target.value)}
                value = {email}
                required  
                // a required attribute
                //controlled input
            />

        <label htmlFor ="password">Password:</label>
        <input 
                type = "password" 
                id = "password" // remove past entries
                onChange = {(e) => setPwd(e.target.value)}
                value = {pwd}
                required  
                // a required attribute
   //controlled input  // Once u click the button , it submits the form.
            />
        <button> Sign In</button>

        
        </form>
        <p> 
            Need an Account?<br/>
            <span className = "Line">
                {
                    /*put router link here*/
                    /*just a place older sign in*/
                }
                <a href ='#' >Sign up </a>  
            </span>
        </p>
        </section>
        )}
        </>  
    ) 
 }
    
    

    
    


export default Login