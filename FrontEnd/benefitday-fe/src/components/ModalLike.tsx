import axios from 'axios';
import React, { useEffect, useState } from 'react';

// -------------type----------------
interface ResponseLike {
  LikeList: Array<Like>;
}

interface Like {
  serviceName: string;
}

interface LikeProps {
  detailInfo: string;
  myIdData: number;
}

interface Count {
  count: number;
}
// -------------type----------------
// -------------css-----------------

const ButtonStyled = {
  width: '90%',
  backgroundColor: '#46a7a1',
  textAlign: 'center',
  padding: '10px',
  margin: 'auto',
  borderRadius: '20px',
  cursor: 'pointer',
  color: 'white',
} as const;

const CountStyled = {
  width: '20%',
  backgroundColor: '#46a7a1',
  textAlign: 'center',
  padding: '5px',
  margin: '20px auto',
  borderRadius: '100%',
  cursor: 'pointer',
  color: 'white',
} as const;
// -------------css-----------------

function ModalLike({ detailInfo, myIdData }: LikeProps) {
  const value = localStorage.getItem('accessToken') as string;
  const [like, setLike] = useState<boolean>(true);
  const [likeCount, setLikeCount] = useState<number>();

  useEffect(() => {
    axios
      .get<ResponseLike>('/like', {
        headers: {
          accessToken: value,
        },
      })
      .then((res) => {
        const likeList = res.data.LikeList;
        const includeLike = likeList.find((item) => item.serviceName === detailInfo);

        if (includeLike) {
          setLike(false);
        }
      })
      .catch((err) => console.log(err));
  }, [detailInfo]);

  useEffect(() => {
    axios
      .get<Count>(`/like/service/${myIdData}`)
      .then((res) => setLikeCount(res.data.count))
      .catch((err) => console.log(err));
  }, [like]);

  // 좋아요, 좋아요취소
  const likeButton = () => {
    const data = { serviceId: myIdData };
    axios
      .post('/like', data, {
        headers: {
          accessToken: value,
        },
      })
      .then(() => {
        setLike(false);
      })
      .catch((err) => console.log(err));
  };

  const likeDeleteButton = () => {
    axios
      .delete(`/like/${myIdData}`, {
        headers: {
          accessToken: value,
        },
      })
      .then(() => {
        setLike(true);
      })
      .catch((err) => console.log(err));
  };
  return (
    <div>
      <div style={CountStyled}>
        <h1>{likeCount}</h1>
      </div>
      {like ? (
        <div style={ButtonStyled} onClick={() => likeButton()}>
          좋아요 누르기
        </div>
      ) : (
        <div style={ButtonStyled} onClick={() => likeDeleteButton()}>
          좋아요 취소
        </div>
      )}
    </div>
  );
}

export default ModalLike;
