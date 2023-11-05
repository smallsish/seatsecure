import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { AuthProvider } from './context/AuthProvider';
import { UserProvider } from './context/UserIDProvider';
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import LoginPage from './pages/Login/LoginPage';
import EventsPage from './pages/Events/EventsPage';
import RegistrationPage from './pages/Registration/RegistrationPage';
import EventDetailsPage from './pages/EventDetails/EventDetailsPage';
import CatSelectionPage from './pages/CatSelection/CatSelectionPage';
import UserProfilePage from './pages/UserProfile/UserProfilePage';
import TicketDetails from './pages/TicketDetails/TicketDetails';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "Login",
    element: <LoginPage />,
  }, {
    path: "Events",
    element: <EventsPage />,
  }, {
    path: "Registration",
    element: <RegistrationPage />,
  }, {
    path: "EventDetails",
    element: <EventDetailsPage />,

  }, {
    path: "CatSelection",
    element: <CatSelectionPage />
  }, {
    path: "UserProfile",
    element: <UserProfilePage />,
  }, {
    path: "TicketDetails",
    element: <TicketDetails />
  }

]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <AuthProvider>
      <UserProvider>
        <RouterProvider router={router} />
      </UserProvider>
    </AuthProvider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
