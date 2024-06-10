import React, { useEffect, useState } from 'react';
import { Grid, Card, CardContent, Typography, CardMedia } from '@mui/material';
import { getAchievements } from '../../services/UserService';
import normalAchievement from './images/medals/normal_achievement.png';
import globalAchievement from './images/medals/global_achievement.png';

const ACHIEVEMENTS = [
  {
    name: "Exceptional Accuracy",
  },
  {
    name: "Tactical Equipment Use",
  },
  {
    name: "Efficiency",
  },
  {
    name: "Team Play",
  },
  {
    name: "Creative Play",
  },
];

export const GLOBAL_ACHIEVEMENTS = [
  {
    name: "50 Kills",
  },
  {
    name: "5 Utility Usages",
  },
  {
    name: "50 Utility Usages",
  },
  {
    name: "5 Assists",
  },
  {
    name: "50 Assists",
  },
];

const Achievements = () => {
  const [userAchievements, setUserAchievements] = useState([]);
  const token = localStorage.getItem('jwtToken');

  useEffect(() => {
    const fetchAchievements = async () => {
      try {
        const data = await getAchievements(token);
        setUserAchievements(data);
      } catch (error) {
        console.error('Error fetching achievements:', error);
      }
    };

    fetchAchievements();
  }, [token]);

  const getAchievementCount = (name) => {
    return userAchievements.filter((achievement) => achievement.name === name).length;
  };

  const isAchievementOwned = (name) => {
    return userAchievements.some((achievement) => achievement.name === name);
  };

  return (
    <Grid container spacing={2} justifyContent="center" height='90vh' width='85vw' paddingBottom={'40px'} marginTop={'20px'}>
      <Grid item xs={12}>
        <Typography variant="h4" gutterBottom>
          Game Achievements
        </Typography>
      </Grid>
      {ACHIEVEMENTS.map((achievement, index) => (
        <Grid item key={index} xs={12} sm={6} md={3} lg={2}>
          <Card>
            <CardMedia
              component="img"
              alt={`${achievement.name} Icon`}
              width="120"
              height="260"
              image={normalAchievement}
              title={achievement.name}
            />
            <CardContent sx={{ paddingBottom: '8px' }}>
              <Typography variant="body2" color="textSecondary">
                {achievement.name}
              </Typography>
              <Typography variant="body2">
                x{getAchievementCount(achievement.name)}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
      <Grid item xs={12}>
        <Typography variant="h4" gutterBottom>
          Global Achievements
        </Typography>
      </Grid>
      {GLOBAL_ACHIEVEMENTS.map((achievement, index) => (
        <Grid item key={index} xs={12} sm={6} md={3} lg={2}>
          <Card>
            <CardMedia
              component="img"
              alt={`${achievement.name} Icon`}
              width="200"
              height="260"
              image={globalAchievement}
              title={achievement.name}
            />
            <CardContent sx={{ paddingBottom: '8px' }}>
              <Typography variant="body2" color="textSecondary">
                {achievement.name}
              </Typography>
              <Typography variant="body2" color={isAchievementOwned(achievement.name) ? "primary" : "error"}>
                {isAchievementOwned(achievement.name) ? "Owned" : "Not Owned"}
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default Achievements;
