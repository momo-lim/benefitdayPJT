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
          alert('성공적으로 갱신했어요!');
        } else {
          throw new Error('오류 발생!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('작성 결과를 갱신하는 중 오류가 발생했어요.');
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
          alert('성공적으로 수정했어요!');
          renewal();
        } else {
          throw new Error('오류 발생!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('수정 중 오류가 발생했어요. 다시 시도해주세요.');
      });
  };

  const deleteInquiry = () => {
    axios
      .delete<object, PostResponse>(`/board/inquiry/${props.id}`, config)
      .then((res) => {
        if (res.data.result === 'success') {
          console.log('success');
          alert('성공적으로 삭제되었어요!');
          renewal();
        } else {
          throw new Error('오류 발생!');
        }
      })
      .catch((err) => {
        alert('삭제 중 오류가 발생했어요. 다시 시도해주세요.');
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
              수정
            </button>
            <button
              className="delete"
              style={deleteStyled}
              onClick={() => {
                console.log('delete');
                deleteInquiry();
              }}
            >
              삭제
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
              1:1 문의하기
            </Typography>
            <TextField
              sx={{ bgcolor: '#ffffff' }}
              label="제목을 입력해주세요"
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
              label="내용을 입력해주세요"
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
              제출하기
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
          alert('성공적으로 갱신했어요!');
        } else {
          throw new Error('오류 발생!');
        }
      })
      .catch((err) => {
        console.log(err);
        alert('작성 결과를 갱신하는 중 오류가 발생했어요.');
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
          alert('1:1 문의가 성공적으로 작성되었어요');
          renewal();
        } else {
          throw new Error('오류 발생!');
        }
      })
      .catch((e) => {
        console.log(e);
        alert('서버로 전송하는 과정에 오류가 발생했어요. 다시 시도해주세요.');
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
            글쓰기
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
                1:1 문의하기
              </Typography>
              <TextField
                sx={{ bgcolor: '#ffffff' }}
                label="제목을 입력해주세요"
                onChange={(e) => {
                  setQuestion((question) => e.target.value);
                }}
              />
              <TextField
                multiline
                rows={4}
                sx={{ bgcolor: '#ffffff' }}
                label="내용을 입력해주세요"
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
                제출하기
              </Button>
            </Stack>
          </Box>
        </Modal>
      </div>
    </React.Fragment>
  );
}

export default Help;
