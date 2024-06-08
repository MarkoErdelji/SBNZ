// src/components/GamesList.js

import React, { useState, useEffect } from 'react';
import { Card, CardContent, Typography, Grid, CircularProgress, Fab, Dialog, DialogTitle, DialogContent, TextField, DialogActions, Button, Box } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { getUnendedGames, createGame } from '../../services/GameService';
import ErrorDialog from '../ErrorDialog'; // Import the ErrorDialog component

const GamesList = () => {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [usernames, setUsernames] = useState(Array(10).fill(''));
  const [errorOpen, setErrorOpen] = useState(false); // State for error dialog
  const [errorMessage, setErrorMessage] = useState(''); // State for error message
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    getUnendedGames(token).then(response => {
      setGames(response.data);
      setLoading(false);
    }).catch(error => {
      console.error("There was an error fetching the games!", error);
      setLoading(false);
    });
  }, []);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleDialogClose = () => { // Close error dialog
    setErrorOpen(false);
  };

  const handleSubmit = () => {
    const token = localStorage.getItem('jwtToken');
    const userDTOs = usernames.map(username => ({ username }));

    createGame(userDTOs, token)
      .then(response => {
        console.log('Game created successfully:', response.data);
        getUnendedGames(token).then(response => {
          setGames(response.data);
        });
        setOpen(false);
      })
      .catch(error => {
        console.error('There was an error creating the game!', error);
        setErrorMessage('There was an error creating the game.    ' + error.response.data.message); // Set error message
        setErrorOpen(true); // Open error dialog
      });
  };

  const handleUsernameChange = (index, value) => {
    const newUsernames = [...usernames];
    newUsernames[index] = value;
    setUsernames(newUsernames);
  };

  if (loading) {
    return <CircularProgress />;
  }

  return (
    <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center', alignItems: 'center', flexDirection: 'column', minHeight: '90vh' }}>
      <Grid container spacing={3} justifyContent="center">
        {games.map(game => (
          <Grid item xs={12} sm={6} md={4} key={game.id}>
            <Card onClick={() => navigate(`/admin/unended-games/${game.id}`)} style={{ cursor: 'pointer' }}>
              <CardContent>
                <Typography variant="h5" component="div">
                  Game ID: {game.id}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Analyzed: {game.analyzed ? 'Yes' : 'No'}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Ended: {game.isEnded ? 'Yes' : 'No'}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      <Fab color="primary" aria-label="add" onClick={handleClickOpen} sx={{ position: 'fixed', bottom: 16, right: 16 }}>
        <AddIcon />
      </Fab>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Create New Game</DialogTitle>
        <DialogContent>
          <Grid container spacing={2}>
            {[...Array(10)].map((_, index) => (
              <Grid item xs={6} key={index}>
                <TextField
                  label={`Username ${index + 1}`}
                  value={usernames[index]}
                  onChange={(e) => handleUsernameChange(index, e.target.value)}
                  fullWidth
                />
              </Grid>
            ))}
          </Grid>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            Cancel
          </Button>
          <Button onClick={handleSubmit} color="primary">
            Submit
          </Button>
        </DialogActions>
      </Dialog>

      <ErrorDialog open={errorOpen} handleClose={handleDialogClose} errorMessage={errorMessage} /> {/* Error dialog */}
    </Box>
  );
};

export default GamesList;
