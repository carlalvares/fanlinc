/* eslint-disable react/jsx-filename-extension */
import React from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import MuiDialogTitle from '@material-ui/core/DialogTitle';
import MuiDialogContent from '@material-ui/core/DialogContent';
import MuiDialogActions from '@material-ui/core/DialogActions';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import Typography from '@material-ui/core/Typography';
import Grid from '@material-ui/core/Grid';
import PropTypes from 'prop-types';


export default function DeleteModal(props) {
  const {
    open, handleClose, post, menuHandleClose, setUpdateTrigger,
  } = props;

  const { postedBy, postedTime } = post;
  const deletePostURL = 'http://localhost:8080/post/deletePost';
  /**
   * Handles the clicking of the delete post button and sends a delete post request to the url:
   * http://localhost:8080/post/deletePost
   */
  function handleSubmit() {
    fetch(deletePostURL, {
      method: 'delete',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      body: JSON.stringify({
        postedBy,
        postedTime,
      }),
    }).then((response) => {
      switch (response.status) {
        case 200:
          setUpdateTrigger(true);
          return Promise.resolve();
        case 404:
          throw new Error('Post not found');
        default:
          throw new Error('Uh Oh! Something went wrong when deleting the post.');
      }
    }).catch((err) => {
      alert(err);
    });
    handleClose();
    menuHandleClose();
  }

  const handleCloseModal = () => {
    handleClose();
    menuHandleClose();
  };

  return (
    <div>
      <Dialog onClose={handleClose} open={open}>
        <MuiDialogTitle>
          <Grid container>
            <Grid item xs={6}>
              <Typography variant="h6">
                Delete Post
              </Typography>
            </Grid>
            <Grid item xs={6}>
              <IconButton onClick={handleCloseModal}>
                <CloseIcon />
              </IconButton>
            </Grid>
          </Grid>
        </MuiDialogTitle>
        <MuiDialogContent dividers>
          <Typography>
            Are you sure you want to delete this post?
          </Typography>
        </MuiDialogContent>
        <MuiDialogActions padding="5px">
          <Button onClick={handleSubmit} color="primary">
            Delete
          </Button>
        </MuiDialogActions>
      </Dialog>
    </div>
  );
}

DeleteModal.propTypes = {
  post: PropTypes.object.isRequired,
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  menuHandleClose: PropTypes.func.isRequired,
  setUpdateTrigger: PropTypes.func.isRequired,
};
