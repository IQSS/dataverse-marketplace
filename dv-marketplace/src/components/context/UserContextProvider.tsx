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
    userRef: MutableRefObject<User | null>;
    login: (user: User) => void;
    logout: () => void;
};

export const UserContext = createContext<UserContextType>({
    user: null,
    userRef: { current: null },
    login: () => {},
    logout: () => {},
});

const UserContextProvider = ({ children }: { children: React.ReactNode }) => {
    
    const [user, setUser] = useState<User | null>(null);
    const userRef: MutableRefObject<User | null> = useRef(null);

    useEffect(() => {
        userRef.current = user;
    }, [user]);

    const login = (user: User) => {
        setUser(user);
    };

    const logout = () => {
        setUser(null);
    };

    return (
        <UserContext.Provider value={{ user: userRef.current, userRef, login, logout }}>
            {children}
        </UserContext.Provider>
    );
};

export default UserContextProvider;
