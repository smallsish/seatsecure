import { useContext } from "react";
import UserContext from "../context/UserIDProvider";

const useUser = () => {
    return useContext(UserContext);
}

export default useUser;