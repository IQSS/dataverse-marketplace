import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { RouterProvider } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './assets/css/index.css';


import router from './app/RouterProvider';

const rootElement = document.getElementById('root');
if (rootElement) {

  createRoot(rootElement).render(
    <StrictMode>
        <ToastContainer />
        <RouterProvider router={router} />
    </StrictMode>,
  );
}
