/* eslint-disable react/jsx-filename-extension */
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import CssBaseline from '@material-ui/core/CssBaseline';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import Divider from '@material-ui/core/Divider';
import DeleteIcon from '@material-ui/icons/Delete';
import Container from '@material-ui/core/Container';
import Fab from '@material-ui/core/Fab';
import useModal from './useModal';
import LeaveFandomModal from './LeaveFandomModal';

/*
Displays the fandom details.
Props
    fandom: the fandom for which this header contains Details
*/
export default function FandomHeader(props) {
  const { fandom, loggedInUser, handleTrigger } = props;
  const { open, handleOpen, handleClose } = useModal();
  /* State of fandom genre & description before and after the fetch call */
  const [fandomGenre, setFandomGenre] = useState('');
  const [fandomDescription, setFandomDescription] = useState('');
  const [fandomPhoto, setFandomPhoto] = useState('');

  useEffect(() => {
    /* The API call this component will make */
    const getFandomDetailsAPI = `http://localhost:8080/fandom/fandomDetails?fandomName=${fandom}`;
    fetch(getFandomDetailsAPI, {
      method: 'GET',
      mode: 'cors',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      switch (response.status) {
        case 200:
          return response.json();
        case 404:
          throw new Error('Fandom not found');
        default:
          throw new Error('Something went wrong when retrieving fandom details');
      }
    })
      .then((data) => {
        setFandomGenre(data.genre);
        setFandomDescription(data.description);
        setFandomPhoto(data.displayPhotoURL);
      }).catch((err) => {
        alert(err);
      });
  }, [fandom]);

  return (
    <>
      <CssBaseline />
      <Container maxWidth="lg">
        {/* Fandom Header Start */}
        <Grid
          container
          direction="row"
          style={{ display: 'flex', width: '65vw' }}
          alignItems="center"
        >
          {/* Grid has 12 columns width - photo:details = 3:9 */}
          <Grid item sm={3}>
            <img src={`${fandomPhoto}`} alt="representing fandom" border="0" width="150px" height="150px" style={{ borderRadius: '50%' }} />
          </Grid>
          <Grid item sm={9}>
            <Fab color="secondary" size="small" onClick={handleOpen}>
              <DeleteIcon />
            </Fab>
            <Typography variant="h2" gutterBottom>{fandom}</Typography>
            <LeaveFandomModal
              loggedInUser={loggedInUser}
              open={open}
              handleClose={handleClose}
              handleTrigger={handleTrigger}
              fandomName={fandom}
            />
            <Typography variant="subtitle2" gutterBottom>
Genre:
              {' '}
              {fandomGenre}
            </Typography>
            <Typography variant="body1" gutterBottom>{fandomDescription}</Typography>
          </Grid>

          {/* Fandom Header End */}
        </Grid>
        <Divider />
      </Container>
    </>
  );
}


FandomHeader.propTypes = {
  fandom: PropTypes.string.isRequired,
  loggedInUser: PropTypes.string.isRequired,
  handleTrigger: PropTypes.func.isRequired,
};
