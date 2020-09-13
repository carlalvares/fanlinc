/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import './App.css';
import {
  BrowserRouter as Router,
  Redirect,
  Route,
  Switch,
} from 'react-router-dom';
import { useCookies } from 'react-cookie';
import Login from './Login';
import Register from './Register';
import Home from './Home';

export default function App() {
  // declare loggedInUser cookie
  const [cookies, setCookie, removeCookie] = useCookies(['loggedInUser']);

  return (
    <Router>
      <Switch>
        <Route exact path="/">
          <Redirect to="/home/feed" />
        </Route>
        <Route path="/home">
          {
              cookies.loggedInUser
              // if loggedInUser cookie is present, render homepage and pass
              // loggedInUser as well as method to remove cookies
                ? (
                  <Home
                    loggedInUser={cookies.loggedInUser}
                    removeCookie={removeCookie}
                  />
                )
              // otherwise, redirect to login page
                : <Redirect to="/login" />
            }
        </Route>
        <Route exact path="/login">
          {
              // if loggedInUser cookie is present, redirect to homepage
              cookies.loggedInUser ? <Redirect to="/" />
              // otherwise, route user to login page
                : <Login setCookie={setCookie} />
            }
        </Route>
        <Route exact path="/register">
          {
              // if loggedInUser cookie is present, redirect to homepage
              cookies.loggedInUser ? <Redirect to="/" />
              // otherwise, route user to registration page
                : <Register setCookie={setCookie} />
            }
        </Route>
      </Switch>
    </Router>
  );
}
