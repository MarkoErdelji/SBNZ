import React, { useState, useEffect } from 'react';
import { Typography, Select, MenuItem, Grid, Paper } from '@mui/material';
import { getUserReport } from '../../services/GameService';

const UserReports = () => {
  const [selectedOption, setSelectedOption] = useState('1d'); 
  const [statsData, setStatsData] = useState(null); 

  const fetchStatsData = async (option) => {
    try {
        const token = localStorage.getItem('jwtToken');
        const response = await getUserReport(token,option);
        setStatsData(response);
    } catch (error) {
      console.error('Error fetching stats data:', error);
    }
  };

  // Fetch stats data on component mount and when the selected option changes
  useEffect(() => {
    fetchStatsData(selectedOption);
  }, [selectedOption]);

  // Handle change in the selected option
  const handleChange = (event) => {
    setSelectedOption(event.target.value);
  };

  return (
    <Grid container justifyContent="center" alignItems="flex-start" spacing={2}>
      <Grid item xs={12}>
        <Typography variant="h4" align="center" gutterBottom>
          User Reports
        </Typography>
      </Grid>
      <Grid item xs={6}>
        <Select value={selectedOption} onChange={handleChange} fullWidth>
          <MenuItem value="1d">Last Day</MenuItem>
          <MenuItem value="7d">Last 7 Days</MenuItem>
          <MenuItem value="30d">Last 30 Days</MenuItem>
        </Select>
      </Grid>
      {statsData && (
        <Grid item xs={12} container justifyContent="center">
          <Grid item xs={6}>
            <Paper elevation={3} style={{ padding: '20px', margin: '10px' }}>
              <Typography variant="h5" gutterBottom>
                Accumulated Stats
              </Typography>
              {/* Display stats data here */}
              <Typography>Total Kills: {statsData.regularKills}</Typography>
              <Typography>Headshot Kills: {statsData.headshotKills}</Typography>
              <Typography>Assists: {statsData.assists}</Typography>
              <Typography>Wallbang Kills: {statsData.wallbangKills}</Typography>
              <Typography>Utility Usages: {statsData.utilityUsages}</Typography>
              <Typography>
                Headshot Percentage: {statsData.headshotPercentage.toFixed(2)}%
            </Typography>
            <Typography>
                Wallbang Percentage: {statsData.wallbangPercentage.toFixed(2)}%
            </Typography>
            </Paper>
          </Grid>
        </Grid>
      )}
    </Grid>
  );
};

export default UserReports;
