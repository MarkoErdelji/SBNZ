<<<<<<< Updated upstream
// src/components/user/UserDashboard.jsx
import React from 'react';

const UserDashboard = () => {
  return (
    <div>
      <h1>User Dashboard</h1>
    </div>
=======
import React, { useEffect, useState } from 'react';
import { Box, Typography, Button, Card, CardContent, CardActions, CircularProgress } from '@mui/material';
import { getUserActiveGameStatistics, performGameAction } from '../../services/GameService';

const UserDashboard = () => {
  const [gameStatistic, setGameStatistic] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchGameStatistics = async () => {
    try {
      const token = localStorage.getItem('jwtToken');
      const response = await getUserActiveGameStatistics(token);
      setGameStatistic(response);
    } catch (error) {
      console.error('Error fetching user active game statistics:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {

    fetchGameStatistics();
  }, []);

  const handleAction = async (action) => {
    try {
      const token = localStorage.getItem('jwtToken');
      const gameId = gameStatistic.gameId;  // Assuming gameId is part of the gameStatistic object
      await performGameAction(gameId, token, action);
      console.log(`Action ${action} performed successfully`);
      fetchGameStatistics();
    } catch (error) {
      console.error(`Error performing action ${action}:`, error);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="100vh">
        <CircularProgress />
      </Box>
    );
  }

  if (!gameStatistic) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="90vh">
        <Typography variant="h5">No active game.</Typography>
      </Box>
    );
  }

  return (
    <Box display="flex" justifyContent="center" alignItems="center" height="90vh" width={"100%"} padding={2}>
      <Card style={{ width: '65%',height: '40%'}}>
        <CardContent>
          <Typography variant="h5" component="div">User Statistics</Typography>
          <Typography>User ID: {gameStatistic.userId}</Typography>
          <Typography variant="h6" component="div">Kills</Typography>
          <Typography>Regular Kills: {gameStatistic.regularKills}</Typography>
          <Typography>Headshot Kills: {gameStatistic.headshotKills}</Typography>
          <Typography>Wallbang Kills: {gameStatistic.wallbangKills}</Typography>
          <Typography variant="h6" component="div">Other Stats</Typography>
          <Typography>Assists: {gameStatistic.assists}</Typography>
          <Typography>Utility Usage: {gameStatistic.utilityUsages}</Typography>
        </CardContent>
        <CardActions>
          <Button variant="contained" color="primary" onClick={() => handleAction('REGULAR_KILL')}>Regular Kill</Button>
          <Button variant="contained" color="primary" onClick={() => handleAction('ASSIST')}>Assist</Button>
          <Button variant="contained" color="primary" onClick={() => handleAction('HEADSHOT_KILL')}>Headshot Kill</Button>
          <Button variant="contained" color="primary" onClick={() => handleAction('WALLBANG_KILL')}>Wallbang Kill</Button>
          <Button variant="contained" color="primary" onClick={() => handleAction('UTILITY_USAGE')}>Utility Usage</Button>
        </CardActions>
      </Card>
    </Box>
>>>>>>> Stashed changes
  );
};

export default UserDashboard;
