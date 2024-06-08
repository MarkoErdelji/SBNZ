import { Route, Routes } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import Login from './components/login/Login';
import Register from './components/register/Register';
import AdminDashboard from './components/admin/AdminDashboard';
import UserDashboard from './components/user/UserDashboard';
import PrivateRoute from './components/routing/PrivateRoute';
import AdminLayout from './components/admin/AdminLayout';
import UserLayout from './components/user/UserLayout';

function App() {
  const token = localStorage.getItem('jwtToken');
  let role = null;

  if (token) {
    try {
      const decodedToken = jwtDecode(token);
      role = decodedToken.role;
    } catch (error) {
      console.error('Invalid token:', error);
    }
  }

  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/admin/*"
        element={
          <PrivateRoute role="ADMIN">
            <AdminLayout>
              <Routes>
                <Route path="" element={<AdminDashboard />} />
              </Routes>
            </AdminLayout>
          </PrivateRoute>
        }
      />
      <Route
        path="/user/*"
        element={
          <PrivateRoute role="USER">
            <UserLayout>
              <Routes>
                <Route path="" element={<UserDashboard />} />
              </Routes>
            </UserLayout>
          </PrivateRoute>
        }
      />
    </Routes>
  );
}

export default App;
