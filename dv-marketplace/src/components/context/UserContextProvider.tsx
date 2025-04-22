import { createContext, useEffect, useState } from 'react';
import type { User, UserContextType } from '../../types/MarketplaceTypes';

export const UserContext = createContext<UserContextType>({
    user: null,
    setUser: () => {},
    showLogin: false,
    setShowLogin: () => {},
    showMessage: false,
    setShowMessage: () => {},
    modalMessage: '',
    setModalMessage: () => {},
    modalTitle: '',
    setModalTitle: () => {}
});

const UserContextProvider = ({ children }: { children: React.ReactNode }) => {
    
    const [user, setUser] = useState<User | null>(null);
    const [showLogin, setShowLogin] = useState(false);
    //App message dialog
    const [showMessage, setShowMessage] = useState(false);
    const [modalMessage, setModalMessage] = useState('');
    const [modalTitle, setModalTitle] = useState('');




    useEffect((): void => {
        const user = localStorage.getItem('user');
        if (user) {
            setUser(JSON.parse(user));
        }
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
                showMessage,
                setShowMessage,
                modalMessage,
                setModalMessage,
                modalTitle,
                setModalTitle
                }}>
            {children}
        </UserContext.Provider>
    );
};

export default UserContextProvider;
