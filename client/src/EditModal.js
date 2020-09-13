/* eslint-disable react/jsx-filename-extension */
import React, { useState, useEffect } from 'react';
import './css/PostModal.css';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Fade from '@material-ui/core/Fade';
import Backdrop from '@material-ui/core/Backdrop';
import Modal from '@material-ui/core/Modal';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import CircularProgress from '@material-ui/core/CircularProgress';
import PropTypes from 'prop-types';
import { useStylesModal } from './materialUIStyle';
import useForm from './useForm';


const editPostURL = 'http://localhost:8080/post/editPost';

export default function EditModal(props) {
  const {
    post, open, handleClose, menuHandleClose, setUpdateTrigger,
  } = props;
  const {
    title, postedBy, postedTime, content, type, level, fandomName, postPhotoUrl,
  } = post;

  const initialValues = {
    title,
    content,
    postPhotoUrl,
    type,
    level,
  };
  const { values, handleChange } = useForm(null, initialValues);

  const [newFandomName, setNewFandomName] = useState(fandomName);
  /**
   * Handles the clicking of the edit post button and sends a edit post request to the url:
   * http://localhost:8080/post/editPost
   */
  function handleSubmit() {
    const {
      title,
      content,
      level,
      postPhotoUrl,
      type,
    } = values;

    fetch(editPostURL, {
      method: 'PATCH',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify({
        title,
        content,
        level,
        postedBy,
        type,
        postedTime,
        postPhotoURL: postPhotoUrl,
      }),
    }).then((response) => {
      switch (response.status) {
        case 200:
          setUpdateTrigger(true);
          return Promise.resolve();
        case 404:
          throw new Error('Username and/or fandom name not found');
        case 400:
          throw new Error('Invalid type/level');
        default:
          throw new Error('Uh Oh! Something went wrong when editing the post.');
      }
    }).catch((err) => {
      alert(err);
    });
  }

  const [fandoms, setFandoms] = useState();
  const getUserFandoms = `http://localhost:8080/account/userFandoms?username=${postedBy}`;
  /**
   * Handles updating the Fandom Dropdown using a get request from the url:
   * http://localhost:8080/account/userFandoms
   */
  useEffect(() => {
    fetch(getUserFandoms, {
      method: 'GET',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
    }).then((response) => {
      switch (response.status) {
        case 200:
          return response.json();
        case 404:
          throw new Error('Username not found');
        default:
          throw new Error('Uh oh! Something went wrong.');
      }
    }).then((data) => {
      setFandoms({ data });
      return Promise.resolve();
    }).catch((err) => {
      alert(err);
    });
  }, [getUserFandoms]);

  const classes = useStylesModal();

  const handleFandomNameChange = (event) => {
    setNewFandomName(event.target.value);
  };

  const handleReset = (event) => {
    values.postPhotoUrl = initialValues.postPhotoUrl;
    values.content = initialValues.content;
    values.title = initialValues.title;
    values.level = initialValues.level;
    values.type = initialValues.type;
    setNewFandomName(fandomName);
    handleChange(event);
  };

  const handleResetClose = (event) => {
    handleReset(event);
    handleClose();
    menuHandleClose();
  };

  const handlePost = (event) => {
    handleSubmit();
    handleResetClose(event);
    menuHandleClose();
  };


  return (
    <Modal
      className={classes.modal}
      open={open}
      onClose={handleClose}
      closeAfterTransition
      BackdropComponent={Backdrop}
      BackdropProps={{
        timeout: 500,
      }}
    >
      <Fade in={open}>
        <Grid
          item
          style={{ textAlign: 'center' }}
          elevation={0}
        >
          <div className={classes.paper}>
            <div className="modal-header">
              <h3>Edit Post</h3>
            </div>
            <div className="modal-body">
              <Grid container spacing={1}>
                <Grid item xs={4}>
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel>
                      Level
                    </InputLabel>
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
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={4}>
                  <FormControl
                    variant="outlined"
                    fullWidth
                  >
                    <InputLabel>
                      Type
                    </InputLabel>
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
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={4}>
                  {fandoms ? (
                    <FormControl
                      variant="outlined"
                      fullWidth
                      disabled
                    >
                      <InputLabel>
                        Fandom
                      </InputLabel>

                      <Select
                        onChange={handleFandomNameChange}
                        value={newFandomName}
                        type="text"
                        required
                        labelWidth={60}
                      >
                        {fandoms.data.userFandoms.map((fandom) => (
                          <MenuItem key={fandom.fandomName} value={fandom.fandomName}>
                            {' '}
                            {fandom.fandomName}
                            {' '}
                          </MenuItem>
                        ))}
                      </Select>
                    </FormControl>
                  ) : (<CircularProgress />)}
                </Grid>
              </Grid>
              <Grid container>
                <Grid item xs={12}>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    label="Title"
                    name="title"
                    type="text"
                    value={values.title}
                    onChange={handleChange}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    label="Paste Image URL Here"
                    name="postPhotoUrl"
                    type="text"
                    value={values.postPhotoUrl}
                    onChange={handleChange}
                  />
                </Grid>
                <Grid item xs={12} zeroMinWidth>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    fullWidth
                    label="Text"
                    name="content"
                    type="text"
                    multiline
                    rows="4"
                    value={values.content}
                    onChange={handleChange}
                  />
                </Grid>
              </Grid>
            </div>
            <div className="modal-footer">
              <Button
                variant="contained"
                onClick={handleResetClose}
                className={classes.button}
                color="default"
              >
                Close
              </Button>
              <Button
                variant="contained"
                className={classes.button}
                onClick={handlePost}
              >
                Save Changes
              </Button>
            </div>
          </div>
        </Grid>
      </Fade>
    </Modal>
  );
}

EditModal.propTypes = {
  post: PropTypes.object.isRequired,
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  menuHandleClose: PropTypes.func.isRequired,
  setUpdateTrigger: PropTypes.func.isRequired,
};
