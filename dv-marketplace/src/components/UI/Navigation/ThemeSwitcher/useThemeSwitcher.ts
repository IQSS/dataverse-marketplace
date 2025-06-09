import { useEffect, useState, useContext } from "react";
import { UserContext } from "../../../context/UserContextProvider";

import { Theme } from "../../../../types/MarketplaceTypes";

export default function useThemeSwitcher() {
    
    const userContext = useContext(UserContext);
    const [themeClass, setThemeClass] = useState("bi bi-circle-half");

    const handleThemeChange = (newTheme: Theme) => {   

        userContext.setTheme(newTheme as Theme);

        document.documentElement.setAttribute("data-bs-theme", userContext.theme || "auto");

        if (newTheme === Theme.AUTO) {
            setThemeClass("bi bi-circle-half");
        } else if (newTheme === Theme.LIGHT) {
            setThemeClass("bi bi-sun");
        } else if (newTheme === Theme.DARK) {
            setThemeClass("bi bi-moon");
        }

        localStorage.setItem("theme", newTheme);
    };

    useEffect(() => {
        
        document.documentElement.setAttribute("data-bs-theme", userContext.theme || "auto");

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