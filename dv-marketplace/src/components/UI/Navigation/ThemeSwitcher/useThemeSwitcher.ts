import { useEffect, useState } from "react";


type Theme = "auto" | "light" | "dark";

export default function useThemeSwitcher() {

    const [theme, setTheme] = useState("auto");
    const [themeClass, setThemeClass] = useState("bi bi-circle-half");

    const handleThemeChange = (newTheme: Theme) => {
        setTheme(newTheme);
        document.documentElement.setAttribute("data-bs-theme", newTheme);
    };

    useEffect(() => {
        if (theme === "auto") {
            setThemeClass("bi bi-circle-half");
        } else if (theme === "light") {
            setThemeClass("bi bi-sun");
        } else if (theme === "dark") {
            setThemeClass("bi bi-moon");
        }
    }, [theme]);

    return {
        theme,
        themeClass,
        setThemeClass,
        handleThemeChange
    };    
}