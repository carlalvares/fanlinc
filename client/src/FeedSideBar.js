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
import AddIcon from '@material-ui/icons/Add';
import Fab from '@material-ui/core/Fab';
import Feed from './Feed';
import FandomHeader from './fandomHeader';

import useModal from './useModal';
import FandomModal from './FandomModal';
import './css/FandomModal.css';

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
  const { loggedInUser } = props;
  const [updateTrigger, setUpdateTrigger] = useState(false);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const { open, handleOpen, handleClose } = useModal();

  useEffect(() => {
    const getFandomListAPI = `http://localhost:8080/account/userFandoms?username=${loggedInUser}`;
    fetch(getFandomListAPI, {
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
          throw new Error('Something went wrong when retrieving fandoms');
      }
    }).then((data) => {
      setUpdateTrigger(false);
      setFandoms(data.userFandoms);
    }).catch((err) => {
      alert(err);
    });
  }, [loggedInUser, updateTrigger]);


  return (
    <>
      <CssBaseline />

      {/* Feed Body */}
      <Grid container direction="row">
        {/* Sidebar Start */}
        {/* Grid has 12 columns width - sidebar:feed = 3:9 */}
        <Grid item sm={3} container direction="column" alignItems='center' style={{ backgroundColor: '#213972', color: 'white', height: 'auto' }}>
          <div className="joinFandomButton" style={{width: '100%'}}>
            <Fab color="primary" variant="extended" size="small" aria-label="add" onClick={handleOpen}>
              <AddIcon className={classes.extendedIcon} />
                Join a New Fandom
            </Fab>
            <FandomModal
              open={open}
              handleClose={handleClose}
              loggedInUser={loggedInUser}
              handleTrigger={setUpdateTrigger}
            />
          </div>
          <Tabs
            orientation="vertical"
            variant="fullWidth"
            value={value}
            onChange={handleChange}
            aria-label="Vertical tabs"
            className={classes.tabs}
            style={{width: '100%', alignItems: 'center'}}
            centered
          >
            {
              fandoms.map((fandom) => (
                <Tab
                  key={fandom.fandomName}
                  label={fandom.fandomName}
                  {...a11yProps(fandoms.indexOf(fandom))}
                  style={{width: '100%'}}
                />
              ))
            }
          </Tabs>
        </Grid>
        {/* Sidebar End */}

        {/* Main Feed Start */}
        <Grid item sm={9} container direction="column" alignItems="center" alignContent="space-around" style={{ backgroundColor: 'white', minHeight: '100vh' }}>
          {fandoms.map((fandom) => (
            <TabPanel key={fandom.fandomName} value={value} index={fandoms.indexOf(fandom)}>
              <FandomHeader
                fandom={fandom.fandomName}
                loggedInUser={loggedInUser}
                handleTrigger={setUpdateTrigger}
              />
              <Feed
                filterParam={fandom.fandomName}
                defaultLevel={fandom.level}
                defaultType={fandom.type}
                loggedInUser={loggedInUser}
                postsType="feed"
              />
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
