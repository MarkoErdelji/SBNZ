// src/components/ErrorDialog.js

import React from 'react';
import { Dialog, DialogTitle, DialogContent, Typography, DialogActions, Button } from '@mui/material';

const ErrorDialog = ({ open, handleClose, errorMessage }) => {
  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>Error</DialogTitle>
      <DialogContent>
        <Typography variant="body1">{errorMessage}</Typography>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary">
          Close
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ErrorDialog;
