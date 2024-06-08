import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Box, TablePagination } from '@mui/material';
import { getUsers } from '../../services/UserService'; // Import the UserService

const getStatusColor = (suspicionLevel) => {
  switch (suspicionLevel) {
    case 'HIGH':
      return 'red';
    case 'MEDIUM':
      return 'orange';
    case 'LOW':
      return 'yellow';
    default:
      return 'green';
  }
};

const getStatusIcon = (suspended) => {
  return suspended ? '✅':'❌';
};

const AdminDashboard = () => {
  const [users, setUsers] = useState([]);
  const [token, setToken] = useState('');
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5); // Changed to default 5 rows per page

  useEffect(() => {
    // Retrieve token from local storage
    const tokenFromStorage = localStorage.getItem('jwtToken');
    setToken(tokenFromStorage);
  }, []);

  useEffect(() => {
    if (token) {
      // Call getUsers function with the token
      const fetchData = async () => {
        const data = await getUsers(token);
        setUsers(data);
      };
      fetchData();
    }
  }, [token]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <div style={{ width: "80%", marginBottom: '20px', textAlign: 'center' }}>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell align="center">Username</TableCell>
              <TableCell align="center">Suspicion Level</TableCell>
              <TableCell align="center">User Type</TableCell>
              <TableCell align="center">Suspended</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {users.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((user) => (
              <TableRow key={user.id}>
                <TableCell align="center">{user.username}</TableCell>
                <TableCell align="center">
                  <Box color={getStatusColor(user.suspicionLevel)}>{user.suspicionLevel}</Box>
                </TableCell>
                <TableCell align="center">{user.userType}</TableCell>
                <TableCell align="center">{getStatusIcon(user.suspended)}</TableCell>
              </TableRow>
            ))}

          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
        component="div"
        count={users.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
        style={{ alignSelf: 'center' }}
      />
    </div>
  );
};

export default AdminDashboard;
