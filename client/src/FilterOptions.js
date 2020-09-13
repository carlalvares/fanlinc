/* eslint-disable react/jsx-filename-extension */
import Grid from '@material-ui/core/Grid';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import React from 'react';
import Button from '@material-ui/core/Button';
import PropTypes from 'prop-types';
import useForm from './useForm';
import { useStylesModal } from './materialUIStyle';
import './css/FilterOptions.css';

export default function FilterOptions(props) {
  const {
    levelFilter, typeFilter, setLevelFilter, setTypeFilter,
  } = props;

  const initialState = {
    level: levelFilter,
    type: typeFilter,
  };

  const { values, handleChange } = useForm(handleApply, initialState);

  function handleApply() {
    const {
      level,
      type,
    } = values;

    setLevelFilter(level);
    setTypeFilter(type);
  }

  const classes = useStylesModal();

  return (
    <div className="filter-options">
      <Grid container spacing={2} justify="center" alignItems="center">
        <Grid item xs={5}>
          <FormControl variant="outlined" fullWidth>
            <InputLabel>Level</InputLabel>
            <Select
              labelWidth={40}
              onChange={handleChange}
              value={values.level}
              required
              name="level"
            >
              <MenuItem value="1">1 - Limited</MenuItem>
              <MenuItem value="2">2 - Casual</MenuItem>
              <MenuItem value="3">3 - Very Involved</MenuItem>
              <MenuItem value="4">4 - Expert</MenuItem>
              <MenuItem value="noFilter">No Filter</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={5}>
          <FormControl variant="outlined" fullWidth>
            <InputLabel>Type</InputLabel>
            <Select
              onChange={handleChange}
              value={values.type}
              required
              labelWidth={35}
              name="type"
            >
              <MenuItem value="General">General</MenuItem>
              <MenuItem value="Vendor/Artist">Vendor/Artist</MenuItem>
              <MenuItem value="Cosplayer">Cosplayer</MenuItem>
              <MenuItem value="noFilter">No Filter</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={2}>
          <Button variant="contained" className={classes.button} onClick={handleApply} fullWidth>
              Apply
          </Button>
        </Grid>
      </Grid>
    </div>
  );
}

FilterOptions.propTypes = {
  levelFilter: PropTypes.string.isRequired,
  typeFilter: PropTypes.string.isRequired,
  setLevelFilter: PropTypes.func.isRequired,
  setTypeFilter: PropTypes.func.isRequired,
};
