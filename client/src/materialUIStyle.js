import React from 'react';
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';
import { makeStyles , createMuiTheme } from '@material-ui/core/styles';
import Image from './img/loginBackground.jpg';
import orange from '@material-ui/core/colors/orange';
import Container from '@material-ui/core/Container';

export function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      Copyright © 
      <Link color="inherit" href="https://material-ui.com/">
        Fanlinc
      </Link>
{' '}
      {new Date().getFullYear()}
      .
    </Typography>
  );
}

export function Footer() {
  return (
    <footer>
      <Container maxWidth="lg">
        <Typography variant="h6" align="center" gutterBottom>
          Fanlinc
        </Typography>
        <Typography variant="subtitle1" align="center" color="textSecondary" component="p">
          Connecting Fans Together
        </Typography>
        <Copyright />
      </Container>
    </footer>
  )
}

export const theme = createMuiTheme({
  palette: {
    primary: orange,
    secondary: {
      main: orange[900],
    },
  },
});

export const useStylesLogin = makeStyles((theme) => ({
  register: {
    height: '135vh',
    background: `url(${Image})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',

  },
  login: {
    height: '100vh',
    background: `url(${Image})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',

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

export const useStylesPosts = makeStyles((theme) => ({
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
    height: '15vw',
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

export const useStylesModal = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  formControl: {
    marginLeft: theme.spacing(0.5),
    marginTop: theme.spacing(0.5),
    minWidth: 80,
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },

  button: {
    color: '#ffffff',
    backgroundColor: '#1D1E3D',
    fontColor: 'white',
    '&:hover': {
      backgroundColor: '#242775',
    },
    marginLeft: theme.spacing(0.5),
    marginRight: theme.spacing(0.5),
  },
  fab: {
    margin: theme.spacing(1),
  },
  extendedIcon: {
    marginRight: theme.spacing(1),
  },
}));
