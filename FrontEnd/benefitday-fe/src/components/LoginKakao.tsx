import React, { useEffect } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { login } from '../modules/user';
import { useNavigate } from 'react-router';

function LoginKakao() {
  // 타입 지정
  interface axiosLogin {
    accessToken: string;
    nickname: string;
    email: string;
    profileImageUrl: string;
    isFirstLogin: boolean;
  }
  // dispatch, navigate
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    // searchParams는 코드가 지정되지 않았다면 null이 된다.
    // 타입스크립트 때문에 null일때를 지정해주어야 한다.
    if (code === null) {
      console.log('code가 없습니다.');
    } else {
      // code가 null값이 아니라면 axios 시작
      axios
        .get<axiosLogin>(`/user/login/${code}`)
        // 정상응답 오면 로컬스토리지에 jwt토큰 저장, redux에 유저정보 저장
        .then((res) => {
          console.log(res);
          localStorage.setItem('accessToken', res.data.accessToken);
          dispatch(
            login({ nickname: res.data.nickname, profileImageUrl: res.data.profileImageUrl, email: res.data.email }),
          );
          // 만약 처음 로그인 했다면 맞춤필터 화면으로, 아니면 메인으로
          if (res.data.isFirstLogin) {
            navigate('/filter');
          } else {
            navigate('/main');
          }
        })
        .catch((err) => console.log(err));
    }
  }, []);

  return (
    <div>
      <h1>Loading...</h1>
    </div>
  );
}

export default LoginKakao;
