import React from 'react';
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Image from './img/loginBackground.jpg';
import orange from '@material-ui/core/colors/orange';
import { createMuiTheme } from '@material-ui/core/styles';

export function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        Fanlinc
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

export const theme = createMuiTheme({
  palette: {
    primary: orange,
    secondary: {
      main: orange[900],
    },
  },
});

export const useStylesLogin = makeStyles(theme => ({
  root: {
    height: '100vh',
    background: `url(${Image})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center'

  },
  image: {
    background: `url(${Image})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
  },
  paper: {
    margin: theme.spacing(4, 4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  palette: {
      primary: 'orange',
  },
  submit: {
    margin: theme.spacing(3, 0, 2),

  },
}));

export const useStylesPosts = makeStyles(theme => ({
  mainFeaturedPost: {
    position: 'relative',
    backgroundColor: theme.palette.grey[800],
    color: theme.palette.common.white,
    backgroundImage: 'Grey',
    backgroundSize: 'cover',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center',
  },
  card: {
     display: 'block',
     width: '55vw',
     transitionDuration: '0.3s',
     height: '15vw'
  },
  cardDetails: {
    flex: 1,
  },
  cardMedia: {
    width: 160,
  },
  footer: {
    backgroundColor: theme.palette.background.paper,
    marginTop: theme.spacing(8),
    padding: theme.spacing(6, 0),
  },
}));
