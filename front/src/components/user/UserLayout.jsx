// src/components/user/UserLayout.jsx
import React from 'react';
import NavbarUser from './NavbarUser';

const UserLayout = ({ children }) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
    <NavbarUser />
    <div style={{display: 'flex',alignItems: "center",justifyContent: "center", flex: 1, width: '100%' }}>
      {children}
    </div>
  </div>
  );
};

export default UserLayout;
