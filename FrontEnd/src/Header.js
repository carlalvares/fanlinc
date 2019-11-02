import React from 'react';
import {useState} from 'react';
import {headerStyle} from './headerStyle';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import AccountCircle from '@material-ui/icons/AccountCircle';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Fanlinclogo from './img/fanlinc_logo.png';

export default function Header(props) {
  const classes = headerStyle();
  const [anchorEl, setAnchorEl] = useState(null);

  const handleClick = event => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    props.setLoggedInUser(null);
    props.setLoggedIn(false);
  }

  return (
      <AppBar position="static" className={classes.appBar}>
        <Toolbar>
          <div>
            <img
                src={Fanlinclogo}
                height="70"
                width="70"
                alt="Fanlinc logo"
            />
          </div>
          <Typography variant="h6" className={classes.title}>
            Fanlinc
          </Typography>
          <div>
            <Typography variant="h6" className={classes.title}>
              {props.loggedInUser}
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
              <AccountCircle/>
            </IconButton>
            <Menu
                anchorEl={anchorEl}
                keepMounted
                open={Boolean(anchorEl)}
                onClose={handleClose}
                getContentAnchorEl={null}
                anchorOrigin={{vertical: "bottom", horizontal: "center"}}
            >
              <MenuItem onClick={handleLogout}>Logout</MenuItem>
            </Menu>
          </div>
        </Toolbar>
      </AppBar>
  )
}
