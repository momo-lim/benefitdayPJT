import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../app/store';
import { Grid, Button, Modal, Box, Stack, Typography, TextField } from '@mui/material';
import axios from 'axios';
import { initSuggestion } from '../modules/suggest';

const divStyled = {
  display: 'flex',
  flexDirection: 'column' as const,
  width: '100%',
  padding: '36px',
  gap: '16px',
};

const textFieldStyled = {
  display: 'flex',
  float: 'right' as const,
  borderStyle: 'solid',
  borderWidth: '2px',
  padding: '12px',
  textAlign: 'center' as const,
  backgroundColor: 'white',
  borderRadius: '10px',
  gap: '8px',
};

const modifyStyled = {
  color: 'white',
  backgroundColor: '#66a073' as const,
  textAlign: 'center' as const,
  marginLeft: 'auto',
  border: 'none',
};

const deleteStyled = {
  color: 'white',
  backgroundColor: '#c38282' as const,
  textAlign: 'center' as const,
  border: 'none',
};

const modalStyled = {
  position: 'absolute' as const,
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
};

interface propsType {
  id: number;
  email: string;
  title: string;
  contents: string;
}

interface Suggest {
  id: number;
  email: string;
  title: string;
  contents: string;
}

interface getResponse {
  result: string;
  suggestionList: Suggest[];
}

interface postResponse {
  data: {
    result: string;
  };
}

const config = {
  headers: {
    accessToken: localStorage.getItem('accessToken') as string,
  },
};

const SuggestionItem = (props: propsType) => {
  const userInfo = useSelector((state: RootState) => state.users.userInfo);

  const [open, setOpen] = useState(false);
  const [modify, setModify] = useState(false);
  const [title, setTitle] = useState(props.title);
  const [content, setContent] = useState(props.contents);
  const dispatch = useDispatch();

  const renewal = () => {
    axios
      .get<getResponse>('/board/suggestion')
      .then((res) => {
        if (res.data.result === 'success') {
          dispatch(initSuggestion(res.data.suggestionList));
          alert('??????????????? ???????????????!');
        } else {
          throw new Error('?????? ??????');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ????????? ???????????? ??? ????????? ???????????????.');
      });
  };

  const modifySuggestion = () => {
    axios
      .put<object, postResponse>(
        `/board/suggestion/${props.id}`,
        {
          contents: content,
          title: title,
        },
        config,
      )
      .then((res) => {
        if (res.data.result === 'success') {
          console.log('success');
          renewal();
        } else {
          throw new Error('?????? ??????');
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const deleteSuggestion = () => {
    axios
      .delete<object, postResponse>(`/board/suggestion/${props.id}`, config)
      .then((res) => {
        if (res.data.result === 'success') {
          renewal();
          console.log('delete');
        } else {
          throw new Error('?????? ??????');
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <React.Fragment>
      <div
        className="text"
        style={textFieldStyled}
        onClick={() => {
          setOpen(true);
        }}
      >
        {props.title}
        {props.email === userInfo.email ? (
          <React.Fragment>
            <button
              className="modify"
              style={modifyStyled}
              onClick={() => {
                setOpen(true);
                setModify(true);
              }}
            >
              ??????
            </button>
            <button
              className="delete"
              style={deleteStyled}
              onClick={() => {
                deleteSuggestion();
                renewal();
              }}
            >
              ??????
            </button>
          </React.Fragment>
        ) : (
          ''
        )}
      </div>
      <Modal
        open={open}
        onClose={() => {
          if (modify) {
            setModify(false);
          }
          setOpen(false);
        }}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={modalStyled}>
          <Stack spacing={3}>
            <Typography id="modal-modal-title" variant="h5" component="h2" margin="normal" sx={{ mb: 3 }}>
              ?????? ????????????
            </Typography>
            <TextField
              sx={{ bgcolor: '#ffffff' }}
              label="????????? ??????????????????"
              defaultValue={title}
              inputProps={{
                readOnly: !modify,
              }}
              onChange={(e) => {
                setTitle((title) => e.target.value);
              }}
            />
            <TextField
              multiline
              rows={4}
              sx={{ bgcolor: '#ffffff' }}
              label="????????? ??????????????????"
              defaultValue={content}
              inputProps={{
                readOnly: !modify,
              }}
              onChange={(e) => {
                setContent((content) => e.target.value);
              }}
            />
            <Button
              variant="contained"
              style={{
                background: '#46a7a1',
                borderColor: '#46a7a1',
                color: 'white',
              }}
              onClick={() => {
                console.log(title);
                console.log(content);

                if (modify) {
                  modifySuggestion();
                  renewal();
                  setModify(false);
                }
                setOpen(false);
              }}
            >
              ????????????
            </Button>
          </Stack>
        </Box>
      </Modal>
    </React.Fragment>
  );
};

function Suggestion() {
  const [writing, setWriting] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  const suggestion_list = useSelector((state: RootState) => state.suggestion.suggestion_list);
  const dispatch = useDispatch();

  const renewal = () => {
    axios
      .get<getResponse>('/board/suggestion')
      .then((res) => {
        if (res.data.result === 'success') {
          dispatch(initSuggestion(res.data.suggestionList));
          alert('??????????????? ???????????????!');
        } else {
          throw new Error('?????? ??????');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ????????? ???????????? ??? ????????? ???????????????.');
      });
  };

  const writeSuggestion = () => {
    axios
      .post<object, postResponse>(
        '/board/suggestion',
        {
          contents: content,
          title: title,
        },
        config,
      )
      .then((res) => {
        if (res.data.result === 'success') {
          console.log(res);
          renewal();
        } else {
          throw new Error('?????? ??????');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ?????? ??? ????????? ???????????????. ?????? ??????????????????.');
      });
  };

  return (
    <div className="suggestion" style={divStyled}>
      <Grid container justifyContent="flex-end">
        <Button
          variant="contained"
          sx={{ width: '20%' }}
          onClick={() => {
            setWriting(true);
          }}
        >
          ?????????
        </Button>
      </Grid>
      {suggestion_list
        ? suggestion_list.map((item, index) => (
            <SuggestionItem key={index} id={item.id} email={item.email} title={item.title} contents={item.contents} />
          ))
        : ''}
      <Modal
        open={writing}
        onClose={() => setWriting(false)}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={modalStyled}>
          <Stack spacing={3}>
            <Typography id="modal-modal-title" variant="h5" component="h2" margin="normal" sx={{ mb: 3 }}>
              ?????? ????????????
            </Typography>
            <TextField
              sx={{ bgcolor: '#ffffff' }}
              label="????????? ??????????????????"
              onChange={(e) => {
                setTitle((title) => e.target.value);
              }}
            />
            <TextField
              multiline
              rows={4}
              sx={{ bgcolor: '#ffffff' }}
              label="????????? ??????????????????"
              onChange={(e) => {
                setContent((content) => e.target.value);
              }}
            />
            <Button
              variant="contained"
              style={{
                background: '#46a7a1',
                borderColor: '#46a7a1',
                color: 'white',
              }}
              onClick={() => {
                console.log(title);
                console.log(content);

                writeSuggestion();
                setWriting(false);
              }}
            >
              ????????????
            </Button>
          </Stack>
        </Box>
      </Modal>
    </div>
  );
}

export default Suggestion;
