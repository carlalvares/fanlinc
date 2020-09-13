/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';


export default function LeaveFandomModal(props) {
  const {
    open, handleClose, fandomName, loggedInUser, handleTrigger,
  } = props;

  const handleCloseModal = () => {
    handleClose();
    handleTrigger(true);
  };

  const leaveFandomURL = 'http://localhost:8080/fandom/leaveFandom';
  /**
   * Handles the clicking of the leave fandom button and sends a leave fandom request to the url:
   * http://localhost:8080/fandom/leaveFandom
   */
  function handleSubmit() {
    // Request to join Fandom
    fetch(leaveFandomURL, {
      method: 'delete',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify({
        fandomName,
        username: loggedInUser,
      }),
    }).then((response) => {
      switch (response.status) {
        case 200:
          return Promise.resolve();
        case 404:
          throw new Error('User is not part of the fandom.');
        default:
          throw new Error('Something went wrong leaving a fandom.');
      }
    }).catch((err) => {
      alert(err);
    });
    handleCloseModal();
  }

  return (
    <div>
      <Dialog onClose={handleClose} open={open}>
        <MuiDialogTitle>
          <Grid container>
            <Grid item xs={6}>
              <Typography variant="h6">
                Leave Fandom
              </Typography>
            </Grid>
            <Grid item xs={6}>
              <IconButton onClick={handleCloseModal}>
                <CloseIcon />
              </IconButton>
            </Grid>
          </Grid>
        </MuiDialogTitle>
        <MuiDialogContent dividers>
          <Typography>
            Are you sure you want to leave this fandom?
          </Typography>
        </MuiDialogContent>
        <MuiDialogActions padding="5px">
          <Button onClick={handleSubmit} color="primary">
            Leave
          </Button>
        </MuiDialogActions>
      </Dialog>
    </div>
  );
}

LeaveFandomModal.propTypes = {
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  fandomName: PropTypes.string.isRequired,
  loggedInUser: PropTypes.string.isRequired,
  handleTrigger: PropTypes.func.isRequired,
};
