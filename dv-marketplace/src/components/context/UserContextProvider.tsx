import { type MutableRefObject, createContext, useEffect, useRef, useState } from 'react';

export type User = {
    id: number;
    username: string;
    email: string;
    roles: string[];
    accessToken: string;
    tokenType: string;
};

export type UserContextType = {
    user: User | null;
    setUser: (user: User | null) => void;
    userRef: MutableRefObject<User | null>;
    login: (user: User) => void;
    logout: () => void;
    show: boolean;
    setShow: (show: boolean) => void;
};

export const UserContext = createContext<UserContextType>({
    user: null,
    setUser: () => {},
    userRef: { current: null },
    login: () => {},
    logout: () => {},
    show: false,
    setShow: () => {},
});

const UserContextProvider = ({ children }: { children: React.ReactNode }) => {
    
    const [user, setUser] = useState<User | null>(null);
    const [show, setShow] = useState(false);

 

    const userRef = useRef<User | null>(null);

    const login = (user: User) => {
        setUser(user);
        userRef.current = user;
    };

    const logout = () => {
        setUser(null);
        userRef.current = null;
    };

    return (
        <UserContext.Provider value={{ user, setUser, userRef, login, logout, show, setShow}}>
            {children}
        </UserContext.Provider>
    );
};

export default UserContextProvider;
