import { Dropdown } from "react-bootstrap";
import { Link } from "react-router-dom";
import { UserContext } from "../../context/UserContextProvider";
import { useContext } from "react";

const NavOptions = () => {

    const userContext = useContext(UserContext);
    
    return (
        <>
        {userContext.user && (
            <Dropdown className="theme-switcher">
            <Dropdown.Toggle variant="{theme}" id="dropdown-basic">
                Admin
            </Dropdown.Toggle>
            <Dropdown.Menu>
                <Dropdown.Item >            
                    {/* <Link to="/install" className="nav-link">Install</Link> */}
                    <Link to="/addExtTool" className="nav-link">Add External Tool</Link>    
                </Dropdown.Item>
            </Dropdown.Menu>
            </Dropdown>
        )}                    
        </>
    );

};

export default NavOptions;