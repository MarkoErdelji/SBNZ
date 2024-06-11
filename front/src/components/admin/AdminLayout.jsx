import React from 'react';
import NavbarAdmin from './NavbarAdmin';

const AdminLayout = ({ children }) => {
  return (
    <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
      <NavbarAdmin />
      <div style={{display: 'flex',alignItems: "center",justifyContent: "center", flex: 1, width: '100%' }}>
        {children}
      </div>
    </div>
  );
};

export default AdminLayout;
