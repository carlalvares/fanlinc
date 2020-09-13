/* eslint-disable react/jsx-filename-extension */
import React, { useState } from 'react';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import PropTypes from 'prop-types';
import Link from '@material-ui/core/Link';
import { headerStyle } from './headerStyle';
import Fanlinclogo from './img/fanlinc_logo.png';

export default function Header(props) {
  const classes = headerStyle();
  const [anchorEl, setAnchorEl] = useState(null);
  const { loggedInUser, removeCookie } = props;

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    // remove loggedInUser cookie to end session and log out user
    removeCookie('loggedInUser', { path: '/' });
  };

  return (
    <AppBar position="static" className={classes.appBar}>
      <Toolbar>
        <div>
          <Link href="/home/feed">
            <img
              src={Fanlinclogo}
              height="70"
              width="70"
              alt="Fanlinc logo"
            />
          </Link>
        </div>
        <Typography variant="h6" className={classes.title}>
            Fanlinc
        </Typography>
        <div>
          <Typography variant="h6" className={classes.title}>
            {loggedInUser}
          </Typography>
        </div>
        <div>
          <IconButton
            aria-label="account of current user"
            aria-controls="menu-appbar"
            aria-haspopup="true"
            color="inherit"
            onClick={handleClick}
          >
            <AccountCircle />
          </IconButton>
          <Menu
            anchorEl={anchorEl}
            keepMounted
            open={Boolean(anchorEl)}
            onClose={handleClose}
            getContentAnchorEl={null}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
          >
            <Link
              color="textPrimary"
              underline="none"
              href={`/home/profile/${loggedInUser}`}
            >
              <MenuItem>
                Profile
              </MenuItem>
            </Link>
            <Link
              color="textPrimary"
              underline="none"
              href="/home/feed"
            >
              <MenuItem>
                Feed
              </MenuItem>
            </Link>
            <MenuItem onClick={handleLogout}>Logout</MenuItem>
          </Menu>
        </div>
      </Toolbar>
    </AppBar>
  );
}

Header.propTypes = {
  loggedInUser: PropTypes.string.isRequired,
  removeCookie: PropTypes.func.isRequired,
};
