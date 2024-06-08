import React, { useEffect, useState } from 'react';
import { Box, Typography, Button, Card, CardContent, CardActions } from '@mui/material';
import { getUserActiveGameStatistics } from '../../services/GameService';

const UserDashboard = () => {
  const [gameStatistic, setGameStatistic] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchGameStatistics = async () => {
      try {
        const token = localStorage.getItem('jwtToken');
        const response = await getUserActiveGameStatistics(token);
        setGameStatistic(response);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching user active game statistics:', error);
        setLoading(false);
      }
    };

    fetchGameStatistics();
  }, []);

  const handleAction = (action) => {
    console.log(`Performing action: ${action}`);
    // Here you can add logic to perform actions based on the user's selection
  };

  if (loading) {
    return <Typography>Loading...</Typography>;
  }

  if (!gameStatistic) {
    return <Typography>No active game.</Typography>;
  }

  return (
    <Card>
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
        <Button variant="contained" color="primary" onClick={() => handleAction('regular_kill')}>Regular Kill</Button>
        <Button variant="contained" color="primary" onClick={() => handleAction('assist')}>Assist</Button>
        <Button variant="contained" color="primary" onClick={() => handleAction('headshot_kill')}>Headshot Kill</Button>
        <Button variant="contained" color="primary" onClick={() => handleAction('wallbang_kill')}>Wallbang Kill</Button>
        <Button variant="contained" color="primary" onClick={() => handleAction('utility_usage')}>Utility Usage</Button>
      </CardActions>
    </Card>
  );
};

export default UserDashboard;
