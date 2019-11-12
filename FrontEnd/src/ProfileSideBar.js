/* eslint-disable react/jsx-filename-extension */
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Typography from '@material-ui/core/Typography';
import Box from '@material-ui/core/Box';
import Grid from '@material-ui/core/Grid';
import Feed from './Feed';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    backgroundColor: theme.palette.background.paper,
    display: 'flex',
    height: 224,
  },
  tabs: {
    borderRight: `1px solid ${theme.palette.divider}`,
  },
}));


function TabPanel(props) {
  const {
    children, value, index, ...other
  } = props;

  return (
    <Typography
      component="div"
      role="tabpanel"
      hidden={value !== index}
      id={`vertical-tabpanel-${index}`}
      aria-labelledby={`vertical-tab-${index}`}
      {...other}
    >
      <Box p={3}>{children}</Box>
    </Typography>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `vertical-tab-${index}`,
    'aria-controls': `vertical-tabpanel-${index}`,
  };
}


export default function SideBar(props) {
  const classes = useStyles();
  const [value, setValue] = useState(0);
  const [fandoms, setFandoms] = useState([]);
  const [picture, setPicture] = useState("");
  const [location, setlocation] = useState("");
  const [bio, setbio] = useState("");
  const { loggedInUser } = props;

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const getFandomListAPI = `http://localhost:8080/account/userFandoms?username=${loggedInUser}`;
  // Need to get pic, location, and bio. Preferably first and last name too
  const getUserDetailsAPI = `http://localhost:8080/account/userDetails?username=${loggedInUser}`;

  useEffect(() => {
    fetch(getUserDetailsAPI, {
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
          throw new Error('Username not found');
        default:
          throw new Error('Something went wrong when retrieving user details');
      }
    })
      .then((data) => {
        setPicture(data.ProfilePhotoUrl);
        setlocation(data.location);
        setbio(data.bio);
      }).catch((err) => {
        alert(err);
      });
  }, []);

  return (
    <>
      <CssBaseline />

      {/* Feed Body */}
      <Grid container>

        {/* Sidebar Start */}
        {/* Grid has 12 columns width - sidebar:feed = 3:9 */}
        <Grid item sm={2} container direction="column" style={{ backgroundColor: '#213972', color: 'white', height: '80vw' }}>
          <typography>

          </typography>
            // { fandoms.map((fandomName) => <Tab key={fandoms.indexOf(fandomName)} label={fandomName} {...a11yProps(fandoms.indexOf(fandomName))} />) }
            /*
            { picture }
            { location }
            */
        </Grid>
        {/* Sidebar End */}

        {/* Main Feed Start */}
        <Grid item sm={10} container direction="column" alignItems="center" alignContent="space-around" style={{ backgroundColor: 'white', minheight: '80vw' }}>
          {fandoms.map((fandomName) => (
            <TabPanel value={value} index={fandoms.indexOf(fandomName)}>
              <Feed filterParam={fandomName} loggedInUser={loggedInUser} postsType="feed" />
            </TabPanel>
          ))}
        </Grid>
        {/* Feed End */}
      </Grid>
    </>
  );
}

SideBar.propTypes = {
  loggedInUser: PropTypes.string.isRequired,
};
