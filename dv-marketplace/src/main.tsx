import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Link, Outlet, RouterProvider, createHashRouter } from 'react-router-dom';
import { ErrorBoundary } from "react-error-boundary";
import {Container, Nav, Navbar} from "react-bootstrap";


import ErrorView from './ErrorView.tsx';

import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';


import App from './App.tsx';

function ErrorFallback({ error, resetErrorBoundary }: { error: Error; resetErrorBoundary: () => void }) {
  return <ErrorView error={error} resetErrorBoundary={resetErrorBoundary} />;
};


const PageIndex = () => {
  return (
      
  <>
    <Navbar bg="dark" variant="dark">
      <Navbar.Brand href="/">
      <span className='d-inline-flex align-items-center' style={{ color: '#C55B28' }}>
        <img src="dv.xhtml" alt="Dataverse Marketplace Logo" style={{ marginRight: '8px' }} />
        Dataverse Marketplace
      </span>
      
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />       
      <Container>
      <Nav className="me-auto">
        {/* <Nav.Link disabled>|</Nav.Link>
        <Nav.Link as={Link} to="/map">Map</Nav.Link>
        <Nav.Link disabled>|</Nav.Link>
        <Nav.Link as={Link} to="/metrics">Metrics</Nav.Link> */}
      </Nav>
      </Container>
    </Navbar>	
  
    <Outlet />
  </>
  );
};

const router = createHashRouter([
  {
      path: '/',
      element: <PageIndex />,
      children: [
        {
          index: true,
          element: <App />,
        },
      ],
  },
]);

createRoot(document.getElementById('root')!).render(
  
  <StrictMode>
    <ErrorBoundary FallbackComponent={ErrorFallback}>
		  <RouterProvider router={router} />
    </ErrorBoundary>
  </StrictMode>,
)
