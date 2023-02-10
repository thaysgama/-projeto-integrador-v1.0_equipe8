import { createContext, useState, useEffect } from "react";

export const AuthContext = createContext();

export const NO_AUTH_STATE = {
    token:"",
    refreshToken:"",
    id:"",
    firstName:"",
    lastName:"",
    email:"",
    role:"",
    favoriteProducts:[]
}


const AuthProvider = ({ children }) =>{
    const [authState, setAuthState] = useState(
        JSON.parse(localStorage.getItem("authState")) ||
        NO_AUTH_STATE
    );

    useEffect(() => {
        localStorage.setItem("authState", JSON.stringify(authState));
      }, [authState]);

    return (
        <AuthContext.Provider
            value={{authState, setAuthState}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthProvider;