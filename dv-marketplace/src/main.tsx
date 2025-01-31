import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { Outlet, RouterProvider, createHashRouter } from 'react-router-dom';
import { ErrorBoundary } from "react-error-boundary";
import ErrorView from './components/ErrorView.tsx';

import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';

import Install from './components/Install.tsx';
import Marketplace from './components/Marketplace.tsx';
import Navigation from './components/Navigation.tsx';

function ErrorFallback({ error, resetErrorBoundary }: { error: Error; resetErrorBoundary: () => void }) {
  return <ErrorView error={error} resetErrorBoundary={resetErrorBoundary} />;
};

const PageIndex = () => {

  return (
      
  <>
    <Navigation />
    <ErrorBoundary FallbackComponent={ErrorFallback}>
      <Outlet />
    </ErrorBoundary>
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
          element: <Marketplace />,
        },{
          path: 'install',
          element: <Install />,
        }
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
