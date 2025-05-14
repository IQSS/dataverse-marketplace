import { useEffect, useState, useContext } from "react";
import { UserContext } from "../../../context/UserContextProvider";

import { Theme } from "../../../../types/MarketplaceTypes";

export default function useThemeSwitcher() {
    
    const userContext = useContext(UserContext);
    const [themeClass, setThemeClass] = useState("bi bi-circle-half");

    const handleThemeChange = (newTheme: Theme) => {
       userContext.setTheme(newTheme);
    };

    

    useEffect(() => {
        
        document.documentElement.setAttribute("data-bs-theme", userContext.theme || "auto");
        const savedTheme = localStorage.getItem("theme") as Theme;

        if (savedTheme !== userContext.theme && userContext.theme !== undefined) {
            console.log("Theme changed to: ", userContext.theme);
            localStorage.setItem("theme", userContext.theme);
        }

        if (userContext.theme === Theme.AUTO) {
            setThemeClass("bi bi-circle-half");
        } else if (userContext.theme === Theme.LIGHT) {
            setThemeClass("bi bi-sun");
        } else if (userContext.theme === Theme.DARK) {
            setThemeClass("bi bi-moon");
        }
        
    }, [userContext.theme]);

    return {
        themeClass,
        setThemeClass,
        handleThemeChange
    };    
}