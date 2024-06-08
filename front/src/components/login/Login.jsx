// src/components/login/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../../services/UserService';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await loginUser({ username: username, password });
      console.log('JWT Token:', response.accessToken);
      // Save token to local storage or state management
      // localStorage.setItem('jwtToken', response.token);
      // Navigate to protected page if needed
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
