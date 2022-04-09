/* eslint-disable @typescript-eslint/no-misused-promises */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../app/store';
import CommentList from './CommentList';
interface LikeProps {
  myIdData: number;
}

interface ResponseComment {
  CommentList: Array<CommentData>;
}

interface CommentData {
  commentId: number;
  contents: string;
  serviceId: number;
  userEmail: string;
  userId: number;
}

interface User {
  profileImageUrl?: string;
  nickname?: string;
  email?: string;
}

// -------------css----------------
const commentDoneButtonStyled = {
  backgroundColor: '#46a7a1',
  color: 'white',
  border: 'none',
  cursor: 'pointer',
};
// -------------css----------------

function ModalComment({ myIdData }: LikeProps) {
  const value = localStorage.getItem('accessToken') as string;
  const [inputValue, setInputValue] = useState('');
  const [commentListItem, setCommentListItem] = useState<Array<CommentData>>();
  const [dataChange, setDataChange] = useState(false);
  const loginUser: User = useSelector((state: RootState) => state.users.userInfo);

  const loginUserEmail = loginUser.email as string;

  const onChangeValue = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setInputValue(event.target.value);
  };

  useEffect(() => {
    const listUp = async () => {
      await axios
        .get<ResponseComment>(`/comment/serviceId=${myIdData}`)
        .then((res) => setCommentListItem(res.data.CommentList))
        .catch((err) => console.log(err));
    };

    void listUp();
  }, [dataChange]);

  const onCreateComment = async () => {
    if (inputValue === '') {
      return;
    }
    const data = {
      contents: inputValue,
      serviceId: myIdData,
    };

    await axios
      .post('/comment', data, {
        headers: {
          accessToken: value,
        },
      })
      .then()
      .catch((err) => console.log(err));

    setDataChange(!dataChange);
    setInputValue('');
  };

  const updateList = () => {
    setDataChange(!dataChange);
  };

  return (
    <div>
      <div style={{ display: 'flex' }}>
        <textarea style={{ width: '90%' }} value={inputValue} onChange={onChangeValue} />

        <button style={commentDoneButtonStyled} onClick={onCreateComment}>
          작성
        </button>
      </div>

      {commentListItem &&
        commentListItem.map((item) => (
          <div key={item.commentId}>
            <CommentList
              commentId={item.commentId}
              contents={item.contents}
              serviceId={item.serviceId}
              userEmail={item.userEmail}
              loginUserEmail={loginUserEmail}
              updateList={updateList}
            ></CommentList>
          </div>
        ))}
    </div>
  );
}

export default ModalComment;
