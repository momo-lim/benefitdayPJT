/* eslint-disable @typescript-eslint/no-misused-promises */
import axios from 'axios';
import React, { useEffect, useState } from 'react';

interface IProps {
  commentId: number;
  contents: string;
  serviceId: number;
  userEmail: string;
  updateList: () => void;
  loginUserEmail: string;
}
// --------------css----------------
const commentBoxStyled = {
  margin: '10px',
  boxShadow: '0 5px 10px rgb(177, 177, 177)',
  borderRadius: '20px',
  padding: '10px',
};

const commentUpdateButtonStyled = {
  margin: '10px',
  backgroundColor: '#46a7a1',
  border: 'none',
  color: 'white',
  cursor: 'pointer',
};

const commentDeleteButtonStyled = {
  backgroundColor: '#a74646',
  border: 'none',
  color: 'white',
  cursor: 'pointer',
};
const commentDoneButtonStyled = {
  backgroundColor: '#46a7a1',
  color: 'white',
  border: 'none',
  cursor: 'pointer',
};
// --------------css----------------

function CommentList({ commentId, contents, userEmail, loginUserEmail, updateList }: IProps) {
  const [commentOpen, setCommentOpen] = useState(false);
  const [updateValue, setUpdateValue] = useState('');
  const value = localStorage.getItem('accessToken') as string;

  useEffect(() => {
    setUpdateValue(contents);
  }, []);

  const onUpdateRequest = (commentId: number) => {
    const data = {
      contents: updateValue,
    };
    axios
      .put(`/comment/${commentId}`, data, {
        headers: {
          accessToken: value,
        },
      })
      .then()
      .catch((err) => console.log(err));
    updateList();
    setCommentOpen(!commentOpen);
  };

  const onUpdateComment = () => {
    setCommentOpen(!commentOpen);
  };
  const onChangeValue = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setUpdateValue(event.target.value);
  };
  const onDeleteComment = async (commentId: number) => {
    await axios
      .delete(`/comment/${commentId}`, {
        headers: {
          accessToken: value,
        },
      })
      .then()
      .catch((err) => console.log(err));
    updateList();
  };

  return (
    <div style={commentBoxStyled}>
      <p>{contents}</p>
      {loginUserEmail === userEmail ? (
        <div>
          <button style={commentUpdateButtonStyled} onClick={onUpdateComment}>
            수정하기
          </button>
          <button style={commentDeleteButtonStyled} onClick={() => onDeleteComment(commentId)}>
            삭제하기
          </button>
        </div>
      ) : (
        ''
      )}
      {commentOpen && (
        <div style={{ display: 'flex' }}>
          <textarea style={{ width: '90%' }} value={updateValue} onChange={onChangeValue} />
          <button style={commentDoneButtonStyled} onClick={() => onUpdateRequest(commentId)}>
            확인
          </button>
        </div>
      )}
    </div>
  );
}

export default CommentList;
