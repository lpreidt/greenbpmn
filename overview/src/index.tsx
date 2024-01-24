import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/Home';
import ProcessDetails from './pages/ProcessDetails';
import ProcessEnergySort from './pages/ProcessEnergySort';


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);


const router = createBrowserRouter([
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/details/:id",
    element: <ProcessDetails />,
  },
  {
  path: "/energy/:energyLevel",
    element: <ProcessEnergySort/>,
  }
], {
  basename: process.env.PUBLIC_URL
});


root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

reportWebVitals();
