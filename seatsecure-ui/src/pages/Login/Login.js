import react from "react";

const Login = () =>{
   
    function sendloginRequest(){
        console.log("im sending a request")
    }


return (
    <div>
      {/* Your login form and other JSX content */}
      
      <button onClick={sendloginRequest}>Login</button>
    </div>
  );
};



export default Login;