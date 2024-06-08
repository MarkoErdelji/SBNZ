// src/components/PrivateRoute.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';

const PrivateRoute = ({ children, role }) => {
  const token = localStorage.getItem('jwtToken');

  if (!token) {
    return <Navigate to="/login" />;
  }

  try {
    const decodedToken = jwtDecode(token);
    if (decodedToken.role !== role) {
      return <Navigate to="/login" />;
    }
    return children;
  } catch (error) {
    console.error('Invalid token:', error);
    return <Navigate to="/login" />;
  }
};

export default PrivateRoute;
