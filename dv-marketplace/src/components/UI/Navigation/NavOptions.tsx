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
                {userContext.user.username}
            </Dropdown.Toggle>
            <Dropdown.Menu>            
                {userContext.user.roles.includes("EDITOR") && (
                    <Dropdown.Item as={Link} to="/addExtTool" className="nav-link">            
                        Add External Tool  
                    </Dropdown.Item>
                )}
                </Dropdown.Menu>
            </Dropdown>
        )}                    
        </>
    );

};

export default NavOptions;