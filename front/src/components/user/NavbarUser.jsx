import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';

const NavbarAdmin = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('jwtToken');
    navigate('/');
  };

  return (
    <AppBar position="static" style={{width:'100vw'}}>
      <Toolbar style={{    justifyContent: "space-between"}}>
        <Typography variant="h6" component={Link} to="/user" style={{ textDecoration: 'none', color: 'inherit' }}>
          User Dashboard
        </Typography>
        <Button
            color="inherit" component={Link} to="/user/hallOfFame" style={{ textDecoration: 'none', color: 'inherit' }}>
            Hall of Fame
          </Button>
        <Button color="inherit" style={{height:'100%',padding:'0px',    maxWidth: '10vw',minHeight:'8vh'}} onClick={handleLogout}>Logout</Button>
        
      </Toolbar>
    </AppBar>
  );
};

export default NavbarAdmin;
