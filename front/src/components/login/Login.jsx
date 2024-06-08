import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../../services/UserService';
import {jwtDecode} from 'jwt-decode'; // Correct import

import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      try {
        const decodedToken = jwtDecode(token);
        console.log('Decoded Token:', decodedToken);
        if (decodedToken.role === 'ADMIN') {
          navigate('/admin');
        } else {
          navigate('/user');
        }
      } catch (error) {
        console.error('Invalid token:', error);
      }
    }
  }, []);

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser({ username, password });
      console.log('JWT Token:', response.accessToken);
      localStorage.setItem('jwtToken', response.accessToken);

      const decodedToken = jwtDecode(response.accessToken);
      console.log(decodedToken);
      if (decodedToken.role === 'ADMIN') {
        navigate('/admin');
      } else {
        navigate('/user');
      }
    } catch (error) {
      console.error('Login error:', error);
    }
  };

  const handleRegister = () => {
    navigate('/register');
  };

  return (
    <div className="login-container">
      <div className="login-header-container">
        <h2>Login</h2>
      </div>
      <form className="login-form" onSubmit={handleLogin}>
        <div className="form-group">
          <input
            type="text"
            placeholder="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div className="button-group">
          <button type="submit" className="login-button">Login</button>
          <button type="button" className="register-button" onClick={handleRegister}>Register</button>
        </div>
      </form>
    </div>
  );
};

export default Login;
