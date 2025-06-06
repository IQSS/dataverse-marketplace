import { createContext, useEffect, useState } from 'react';
import type { User, UserContextType } from '../../types/MarketplaceTypes';
import { Theme } from '../../types/MarketplaceTypes';

export const UserContext = createContext<UserContextType>({
    user: null,
    setUser: () => {},
    showLogin: false,
    setShowLogin: () => {},   
    theme: Theme.AUTO,
    setTheme: () => {}

});

const UserContextProvider = ({ children }: { children: React.ReactNode }) => {
    
    const [user, setUser] = useState<User | null>(null);
    const [showLogin, setShowLogin] = useState(false);
    //App message dialog    
    const [theme, setTheme] = useState<Theme>(Theme.AUTO);

    useEffect((): void => {
        const user = localStorage.getItem('user');
        if (user) {
            setUser(JSON.parse(user));
        }

        const savedTheme = localStorage.getItem("theme");
        if (savedTheme) {
           setTheme(savedTheme as Theme);
        } else {
           setTheme(Theme.AUTO);
        }

        console.log(savedTheme)

    }, []);

    useEffect((): void => {
        if (user) {
            localStorage.setItem('user', JSON.stringify(user));
        } else {
            localStorage.removeItem('user');
        }
    }, [user]);

    

    return (
        <UserContext.Provider value={{ 
                user, 
                setUser, 
                showLogin, 
                setShowLogin,              
                theme,
                setTheme
                }}>
            {children}
        </UserContext.Provider>
    );
};

export default UserContextProvider;
