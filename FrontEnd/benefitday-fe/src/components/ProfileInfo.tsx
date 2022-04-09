import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../app/store';
import { useNavigate } from 'react-router-dom';
import { Modal, Box, Stack, Button, Typography } from '@mui/material';
import axios from 'axios';
import ModalDetail from './ModalDetail';
import { logout } from '../modules/user';

function ProfileInfo() {
  const [leave, setLeave] = useState(false);
  const [likeList, setLikeList] = useState<Array<ListData>>();
  const [open, setOpen] = useState(false);
  const [id, setId] = useState<number>();

  interface User {
    profileImageUrl?: string;
    nickname?: string;
  }

  interface ResponseData {
    LikeList: Array<ListData>;
  }

  interface ListData {
    serviceId: number;
    serviceName: string;
  }
  const loginUser: User = useSelector((state: RootState) => state.users.userInfo);
  // ---------------------css-------------------
  const imgStyled = {
    height: '250px',
    width: '250px',
    borderRadius: '50%',
  };

  const divStyled = {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    // maxWidth: '500px',
    margin: 'auto',
    transitionDuration: '1s',
    padding: '32px',
  } as const;

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

  const likeListStyled = {
    padding: '5px',
    boxShadow: '0 5px 10px rgb(177, 177, 177)',
    borderRadius: '20px',
    backgroundColor: 'white',
    margin: '10px',
    width: '250px',
  };
  const DeleteButtonStyled = {
    backgroundColor: '#a74646',
    border: 'none',
    color: 'white',
    cursor: 'pointer',
    margin: '10px',
  };
  const filterButtonStyled = {
    backgroundColor: '#46a7a1',
    color: 'white',
    border: 'none',
    cursor: 'pointer',
    margin: '10px',
  };
  // ---------------------css-------------------

  const value = localStorage.getItem('accessToken') as string;
  useEffect(() => {
    axios
      .get<ResponseData>('/like', {
        headers: {
          accessToken: value,
        },
      })
      .then((res) => {
        console.log(res);
        setLikeList(res.data.LikeList);
      })
      .catch((err) => console.log(err));
  }, []);

  const CloseModal = (e: React.ChangeEvent) => {
    if (e.target.id === 'in') {
      setOpen(false);
    } else {
      return;
    }
  };

  const OpenModal = (id: number) => {
    setId(id);
    setOpen(true);
  };

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const onUserDelete = () => {
    axios
      .delete('/user/delete', {
        headers: {
          accessToken: value,
        },
      })
      .then(() => {
        dispatch(logout());
        localStorage.removeItem('accessToken');
        navigate('/');
      })

      .catch((err) => console.log(err));
  };
  return (
    <div style={divStyled}>
      <img style={imgStyled} src={loginUser.profileImageUrl} alt="" />
      <h1>{loginUser.nickname}</h1>

      <div className="pages">
        <button
          style={filterButtonStyled}
          onClick={() => {
            console.log('move');
            navigate('/filter');
          }}
        >
          맞춤필터 설정
        </button>
        <button
          style={DeleteButtonStyled}
          onClick={() => {
            setLeave(true);
          }}
        >
          회원탈퇴
        </button>
        <Modal
          open={leave}
          onClose={() => setLeave(false)}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box sx={modalStyled}>
            <Stack spacing={3} alignItems="center">
              <Typography id="modal-modal-title" variant="h5" component="h2" margin="normal" sx={{ mb: 3 }}>
                정말 탈퇴하시겠어요?
              </Typography>
              <Stack spacing={2} direction="row">
                <Button
                  onClick={onUserDelete}
                  variant="contained"
                  style={{
                    background: '#ff3636',
                    borderColor: '#ff3636',
                    color: 'white',
                  }}
                >
                  예
                </Button>
                <Button
                  variant="contained"
                  onClick={() => {
                    setLeave(false);
                  }}
                >
                  아니오
                </Button>
              </Stack>
            </Stack>
          </Box>
        </Modal>
      </div>
      <h2>좋아요 한 정책들</h2>
      <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'space-between' }}>
        {likeList &&
          likeList.map((item, i) => (
            <div style={likeListStyled} key={i} onClick={() => OpenModal(item.serviceId)}>
              <p>{item.serviceName}</p>
            </div>
          ))}
      </div>

      {open && <ModalDetail modalClose={CloseModal} idData={id}></ModalDetail>}
    </div>
  );
}

export default ProfileInfo;
