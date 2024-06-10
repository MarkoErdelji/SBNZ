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
        <div>
        <Typography variant="h6" component={Link} to="/user" style={{ textDecoration: 'none', color: 'inherit' }}>
          User Dashboard
        </Typography>
          <Button
          color="inherit" component={Link} to="/user/achievements" style={{ textDecoration: 'none', color: 'inherit', height:'100%',marginLeft:'20px',    maxWidth: '10vw',minHeight:'8vh' }}>
          Achievements
        </Button>
        <Button
          color="inherit" component={Link} to="/user/reports" style={{ textDecoration: 'none', color: 'inherit', height:'100%',marginLeft:'20px',    maxWidth: '10vw',minHeight:'8vh' }}>
          Reports
        </Button>
        <Button
            color="inherit" component={Link} to="/user/hallOfFame" style={{ textDecoration: 'none', color: 'inherit', height:'100%',marginLeft:'20px',    maxWidth: '10vw',minHeight:'8vh' }}>
            Hall of Fame
          </Button>
        </div>
        <Button color="inherit" style={{height:'100%',padding:'0px',    maxWidth: '10vw',minHeight:'8vh'}} onClick={handleLogout}>Logout</Button>
        
      </Toolbar>
    </AppBar>
  );
};

export default NavbarAdmin;
