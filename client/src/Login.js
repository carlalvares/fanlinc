/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Paper from '@material-ui/core/Paper';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { ThemeProvider } from '@material-ui/core/styles';
import Link from '@material-ui/core/Link';
import PropTypes from 'prop-types';
import Fanlinclogo from './img/fanlinc_logo.png';
import useForm from './useForm';
import { Copyright, theme, useStylesLogin } from './materialUIStyle';

const initialState = {
  username: '',
  password: '',
};

export default function Login(props) {
  // eslint-disable-next-line no-use-before-define
  const { values, handleChange, handleSubmit } = useForm(submit, initialState);
  const { setCookie } = props;

  /**
     * Handles the clicking of the submit button and sends a post request to the url:
     * http://localhost:8080/account/validateUser
     */
  function submit() {
    const {
      username,
      password,
    } = values;

    const validateUserURL = `http://localhost:8080/account/validateUser?username=${username}&password=${password}`;

    fetch(validateUserURL, {
      method: 'get',
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
          throw new Error('User does not exist');
        default:
          throw new Error('Error occurred during login');
      }
    }).then((data) => {
      if (data.accepted) {
        setCookie('loggedInUser', data.username, { path: '/' });
        return Promise.resolve();
      }
      throw new Error('Incorrect password');
    })
      .catch((err) => {
        alert(err);
      });
  }

  const classes = useStylesLogin();

  return (
    <ThemeProvider theme={theme}>
      <Grid
        container
        component="main"
        className={classes.login}
        direction="column"
        alignItems="center"
        justify="center"
      >
        <CssBaseline />
        <Grid
          item
          xs={4}
          style={{ textAlign: 'center' }}
          component={Paper}
          elevation={0}
          square
        >
          <div className={classes.paper}>
            <img src={Fanlinclogo} height="70" width="70" alt="Fanlinc Logo" />
            <Typography component="h1" variant="h5">
                            Fanlinc Login
            </Typography>
            <form className={classes.form} onSubmit={handleSubmit}>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                label="Username"
                name="username"
                onChange={handleChange}
                autoFocus
              />
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                onChange={handleChange}
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="Remember me"
              />
              <Button
                type="submit"
                fullWidth
                variant="contained"
                className={classes.submit}
                color="primary"
              >
                                Sign In
              </Button>
              <Grid container>
                <Grid item>
                  <Link href="/register" variant="body2">
                    Don&apos;t have an account? Sign Up
                  </Link>
                </Grid>
              </Grid>
              <Box mt={5}>
                <Copyright />
              </Box>
            </form>
          </div>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}

Login.propTypes = {
  setCookie: PropTypes.func.isRequired,
};
