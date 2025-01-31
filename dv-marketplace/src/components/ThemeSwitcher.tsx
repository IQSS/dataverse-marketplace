import { useState } from "react";
import { Dropdown } from "react-bootstrap";



type Theme = 'auto' | 'light' | 'dark';

const ThemeSwitcher = () => {

    const [theme, setTheme] = useState('auto');
  
    

    const handleThemeChange = (newTheme: Theme) => {
        setTheme(newTheme);
        document.documentElement.setAttribute('data-bs-theme', newTheme);
    };
  
    return (
       <Dropdown className="me-2">
            <Dropdown.Toggle variant="{theme}" id="dropdown-basic">
            {theme === 'dark' ? (
                <i className="bi bi-moon"></i>
            ) : theme === 'light' ? (
                <i className="bi bi-sun"></i>
            ) : (
                <i className="bi bi-circle-half"></i>
            )}
            </Dropdown.Toggle>

            <Dropdown.Menu>
            <Dropdown.Item onClick={() => handleThemeChange('auto')} >
                <i className="bi bi-circle-half"></i>
            </Dropdown.Item>
            <Dropdown.Item onClick={() => handleThemeChange('light')}>
                <i className="bi bi-sun"></i>
            </Dropdown.Item>
            <Dropdown.Item onClick={() => handleThemeChange('dark')}>
                <i className="bi bi-moon"></i>
            </Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
      
    );
  };

  export default ThemeSwitcher;
