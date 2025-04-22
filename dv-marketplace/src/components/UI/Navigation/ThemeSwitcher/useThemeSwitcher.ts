import { useEffect, useState } from "react";

type Theme = "auto" | "light" | "dark";

export default function useThemeSwitcher() {

    const [theme, setTheme] = useState<Theme>();
    const [themeClass, setThemeClass] = useState("bi bi-circle-half");

    const handleThemeChange = (newTheme: Theme) => {
        setTheme(newTheme);
    };

    useEffect(() => {
        const savedTheme = localStorage.getItem("theme");
        if (savedTheme) {
            setTheme(savedTheme as Theme);
            console.log("Theme loaded from local storage");
            console.log(savedTheme);
        } else {
            setTheme("auto");
        }
    }, []);

    useEffect(() => {
        
        document.documentElement.setAttribute("data-bs-theme", theme || "auto");
        const savedTheme = localStorage.getItem("theme") as Theme;

        if (savedTheme !== theme && theme !== undefined) {
            localStorage.setItem("theme", theme);
            console.log("Theme saved to local storage");
            console.log(theme);
        }

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