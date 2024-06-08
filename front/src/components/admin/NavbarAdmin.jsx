import React from 'react';
import { AppBar, Toolbar, Typography, Button } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';

const NavbarAdmin = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('jwtToken');
    navigate('/login');
  };

  return (
    <AppBar position="static" style={{width:'100vw'}}>
      <Toolbar style={{    justifyContent: "space-between"}}>
        <div>
        <Typography variant="h6" component={Link} to="/admin" style={{ textDecoration: 'none', color: 'inherit' }}>
          Admin Dashboard
        </Typography>
        <Button component={Link}  style={{height:'100%',marginLeft:'20px',    maxWidth: '10vw',minHeight:'8vh'}}to="/admin/unended-games" color="inherit">
            Games
          </Button>
        </div>
        <Button color="inherit" style={{height:'100%',padding:'0px',    maxWidth: '10vw',minHeight:'8vh'}} onClick={handleLogout}>Logout</Button>
      </Toolbar>
    </AppBar>
  );
};

export default NavbarAdmin;
