import { Dropdown } from "react-bootstrap";
import useThemeSwitcher from "./useThemeSwitcher";
import { Theme } from "../../../../types/MarketplaceTypes";


const ThemeSwitcher = () => {

    const {
        handleThemeChange,
        themeClass,
    } = useThemeSwitcher();
  
    return (
       <Dropdown className="theme-switcher">
            <Dropdown.Toggle variant="{theme}" id="dropdown-basic">
                <i className={themeClass} />
            </Dropdown.Toggle>

            <Dropdown.Menu>
            <Dropdown.Item onClick={() => handleThemeChange(Theme.AUTO)} >
                <i className="bi bi-circle-half" />
            </Dropdown.Item>
            <Dropdown.Item onClick={() => handleThemeChange(Theme.LIGHT)}>
                <i className="bi bi-sun" />
            </Dropdown.Item>
            <Dropdown.Item onClick={() => handleThemeChange(Theme.DARK)}>
                <i className="bi bi-moon" />
            </Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
      
    );
  };

  export default ThemeSwitcher;
