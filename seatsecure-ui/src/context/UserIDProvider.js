import { createContext, useState } from 'react';

const UserContext = createContext({});

export const UserProvider = ({children}) => {
    const[userID,setUserID] = useState({});

  return (
    <UserContext.Provider value={{ userID, setUserID}}>
      {children}
    </UserContext.Provider>
  );
}
export default UserContext;