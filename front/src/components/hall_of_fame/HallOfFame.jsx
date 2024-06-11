import React, { useState, useEffect } from 'react';
import { Container, Box, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import EmojiEventsIcon from '@mui/icons-material/EmojiEvents';
import MilitaryTechIcon from '@mui/icons-material/MilitaryTech';
import backgroundImage from './images/background.jpg';
import { getWinners } from '../../services/GameService';

const HallOfFame = () => {
  const [yearCity, setYearCity] = useState('');
  const [winners, setWinners] = useState([]);

  const handleChange = (event) => {
    setYearCity(event.target.value);
    
  };

  useEffect(() => {
    const fetchWinners = async (city) => {
      await getWinners(city)
        .then((result) => {
          console.log(result.data)
          setWinners(result.data.winners);
          console.log(result.data[0])

        })
        .catch((err) => {
          console.error('Error fetching winners:', err);
        });
    };
    if(yearCity !== ''){
      
      fetchWinners(yearCity);
      
    }
    
  }, [yearCity]);

 
  return (
    <Box
      sx={{
        height: '92vh',
        display: 'flex',
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center',
        backgroundImage: `url(${backgroundImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
      }}
    >
      <Container style={{ marginTop: '20px', display: 'flex', alignItems: 'center', flexDirection: 'column' }}>
        <FormControl variant="filled" sx={{ minWidth: '250px', background: 'white' }}>
          <InputLabel id="year-city-select-label">Select Tournament</InputLabel>
          <Select
            labelId="year-city-select-label"
            id="year-city-select"
            value={yearCity}
            onChange={handleChange}
            label="Select Tournament"
          >
            <MenuItem value="Copenhagen 2022">Copenhagen 2022</MenuItem>
            <MenuItem value="Copenhagen 2021">Copenhagen 2021</MenuItem>
            <MenuItem value="Paris 2022">Paris 2022</MenuItem>
            <MenuItem value="Paris 2021">Paris 2021</MenuItem>
          </Select>
        </FormControl>
        <Box
          sx={{
            width: '300px',
            marginTop: '20px',
            padding: '20px',
            backgroundColor: 'white',
            borderRadius: '8px',
            boxShadow: '0 0 10px rgba(0,0,0,0.1)',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          <h1>
            <EmojiEventsIcon sx={{ color: 'Yellow' }} fontSize="large" /> {winners.length === 0 ? "TEAM" : winners[0]}
          </h1>
          <Box sx={{ width: '100%', display: 'flex', flexDirection: 'column' }}>
            {winners.length === 0 ? (
              <>
                <h3>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> Player
                </h3>
                <h3>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> Player
                </h3>
                <h3>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> Player
                </h3>
                <h3>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> Player
                </h3>
                <h3>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> Player
                </h3>
              </>
            ) : (
              winners.slice(1, 6).map((winner, index) => (
                <h3 key={index}>
                  <MilitaryTechIcon sx={{ color: 'Yellow' }} fontSize="medium" /> {winner}
                </h3>
              ))
            )}
          </Box>
        </Box>
      </Container>
    </Box>
  );
};

export default HallOfFame;
