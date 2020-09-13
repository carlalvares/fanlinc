/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import PropTypes from 'prop-types';
import {
  Switch, Route, useRouteMatch,
} from 'react-router-dom';
import Header from './Header';
import SideBar from './FeedSideBar';
import ProfileSideBar from './ProfileSideBar';

export default function Home(props) {
  const { loggedInUser, removeCookie } = props;
  const { path } = useRouteMatch();

  return (
    <div>
      <Header
        loggedInUser={loggedInUser}
        removeCookie={removeCookie}
      />
      <Switch>
        <Route exact path={`${path}/feed`}>
          <SideBar
            loggedInUser={loggedInUser}
          />
        </Route>
        <Route path={`${path}/profile/:username`}>
          <ProfileSideBar
            loggedInUser={loggedInUser}
          />
        </Route>
      </Switch>
    </div>
  );
}

Home.propTypes = {
  loggedInUser: PropTypes.string.isRequired,
  removeCookie: PropTypes.func.isRequired,
};
