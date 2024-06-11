import { Route, Routes } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import Login from './components/login/Login';
import Register from './components/register/Register';
import AdminDashboard from './components/admin/AdminDashboard';
import UserDashboard from './components/user/UserDashboard';
import PrivateRoute from './components/routing/PrivateRoute';
import AdminLayout from './components/admin/AdminLayout';
import UserLayout from './components/user/UserLayout';
import GamesList from './components/admin/GameList';
import GameDetails from './components/admin/GameDetails';
import HallOfFame from './components/hall_of_fame/HallOfFame.jsx';
import Achievements from './components/user/Achievements.jsx';
import UserReports from './components/user/UserReports.jsx';

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
      <Route path="/" element={<Login />} />

      <Route
        path="/admin/*"
        element={
          <PrivateRoute role="ADMIN">
            <AdminLayout>
              <Routes>
                <Route path="" element={<AdminDashboard />} />
                <Route path="/unended-games" element={<GamesList />} />
                <Route path="/unended-games/:gameId" element={<GameDetails />} />
                <Route path="/create-user" element={<Register />} />
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
                <Route path="/hallOfFame" element={<HallOfFame />}/>
                <Route path="/achievements" element={<Achievements />} />
                <Route path="/reports" element={<UserReports />} />
              </Routes>
            </UserLayout>
          </PrivateRoute>
        }
      />
    </Routes>
  );
}

export default App;
