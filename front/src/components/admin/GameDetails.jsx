// src/components/GameDetails.js

import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Button, Box, Typography } from '@mui/material';
import { endGame } from '../../services/GameService';

const GameDetails = () => {
  const { gameId } = useParams();
  const navigate = useNavigate();

  const handleEndGame = () => {
    const token = localStorage.getItem('jwtToken');
    endGame(gameId, token).then(() => {
      navigate('/admin/unended-games');
    }).catch(error => {
      console.error("There was an error ending the game!", error);
    });
  };

  return (
    <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', minHeight: '90vh',height:'90vh' }}>
        <div style={{height:'100%',display: 'flex', justifyContent: 'space-evenly', alignItems: 'center', flexDirection: 'column',}}>
      <Typography variant="h4">Game ID: {gameId}</Typography>
      <div style={{display:"flex",justifyContent: 'center', alignItems: 'center'}}>
        <Button variant="contained" color="primary" onClick={handleEndGame} sx={{ marginTop: 2 }}>
            End Game
        </Button>
      </div>
      </div>
    </Box>
  );
};

export default GameDetails;
