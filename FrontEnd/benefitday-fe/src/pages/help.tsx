import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Grid, Modal, Stack, TextField, Box, Typography } from '@mui/material';
import { RootState } from '../app/store';
import { initQnA } from '../modules/proposal';
import axios from 'axios';

interface propsType {
  id: number;
  email: string;
  serviceId: number;
  title: string;
  contents: string;
}

interface QnA {
  id: number;
  email: string;
  serviceId: number;
  title: string;
  contents: string;
}

interface InquiryResponse {
  result: string;
  inquiryList: QnA[];
}

interface PostResponse {
  data: {
    result: string;
  };
}

const qnaStyled = {
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

const config = {
  headers: {
    accessToken: localStorage.getItem('accessToken') as string,
  },
};

const InputField = (props: propsType) => {
  const [open, setOpen] = useState(false);
  const [modify, setModify] = useState(false);
  const [title, setTitle] = useState(props.title);
  const [content, setContent] = useState(props.contents);

  const userInfo = useSelector((state: RootState) => state.users.userInfo);
  const dispatch = useDispatch();

  const renewal = () => {
    axios
      .get<InquiryResponse>('/board/inquiry')
      .then((res) => {
        if (res.data.result === 'success') {
          dispatch(initQnA(res.data.inquiryList));
          alert('??????????????? ???????????????!');
        } else {
          throw new Error('?????? ??????!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ????????? ???????????? ??? ????????? ???????????????.');
      });
  };

  const modifyInquiry = () => {
    axios
      .put<object, PostResponse>(
        `/board/inquiry/${props.id}`,
        { contents: content, serviceId: props.serviceId, title: title },
        config,
      )
      .then((res) => {
        console.log(res);

        if (res.data.result === 'success') {
          alert('??????????????? ???????????????!');
          renewal();
        } else {
          throw new Error('?????? ??????!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ??? ????????? ???????????????. ?????? ??????????????????.');
      });
  };

  const deleteInquiry = () => {
    axios
      .delete<object, PostResponse>(`/board/inquiry/${props.id}`, config)
      .then((res) => {
        if (res.data.result === 'success') {
          console.log('success');
          alert('??????????????? ??????????????????!');
          renewal();
        } else {
          throw new Error('?????? ??????!');
        }
      })
      .catch((err) => {
        alert('?????? ??? ????????? ???????????????. ?????? ??????????????????.');
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
                console.log('delete');
                deleteInquiry();
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
              1:1 ????????????
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
                  modifyInquiry();
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

function Help() {
  const [writing, setWriting] = useState(false);
  const [question, setQuestion] = useState('');
  const [answer, setAnswer] = useState('');

  const contents = useSelector((state: RootState) => state.proposal.qna_list);
  const dispatch = useDispatch();

  const renewal = () => {
    axios
      .get<InquiryResponse>('/board/inquiry')
      .then((res) => {
        if (res.data.result === 'success') {
          dispatch(initQnA(res.data.inquiryList));
          alert('??????????????? ???????????????!');
        } else {
          throw new Error('?????? ??????!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('?????? ????????? ???????????? ??? ????????? ???????????????.');
      });
  };

  const writeInquiry = () => {
    axios
      .post<object, PostResponse>(
        '/board/inquiry',
        {
          contents: answer,
          serviceId: 2,
          title: question,
        },
        config,
      )
      .then((res) => {
        console.log(res);
        if (res.data.result === 'success') {
          alert('1:1 ????????? ??????????????? ??????????????????');
          renewal();
        } else {
          throw new Error('?????? ??????!');
        }
      })
      .catch((e) => {
        console.log(e);
        alert('????????? ???????????? ????????? ????????? ???????????????. ?????? ??????????????????.');
      });
  };

  return (
    <React.Fragment>
      <div className="help" style={qnaStyled}>
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
        {contents
          ? contents.map((item, index) => (
              <InputField
                key={index}
                id={item.id}
                serviceId={item.serviceId}
                email={item.email}
                title={item.title}
                contents={item.contents}
              />
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
                1:1 ????????????
              </Typography>
              <TextField
                sx={{ bgcolor: '#ffffff' }}
                label="????????? ??????????????????"
                onChange={(e) => {
                  setQuestion((question) => e.target.value);
                }}
              />
              <TextField
                multiline
                rows={4}
                sx={{ bgcolor: '#ffffff' }}
                label="????????? ??????????????????"
                onChange={(e) => {
                  setAnswer((answer) => e.target.value);
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
                  console.log(question);
                  console.log(answer);

                  writeInquiry();
                  setWriting(false);
                }}
              >
                ????????????
              </Button>
            </Stack>
          </Box>
        </Modal>
      </div>
    </React.Fragment>
  );
}

export default Help;
