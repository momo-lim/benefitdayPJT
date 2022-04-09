import React, { useEffect } from 'react';
import logo from '../images/logo.gif';
import kakao_medium from '../images/kakao_medium.png';
import { useSelector } from 'react-redux';
import { RootState } from '../app/store';
import { useNavigate } from 'react-router-dom';

function Login() {
  const REST_API_KEY = process.env.REACT_APP_KAKAO_API_KEY as string;
  const REDIRECT_URI = 'http://localhost:3000/oauth';
  const KAKAO = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}`;
  const loginUser = useSelector((state: RootState) => state.users.isLogin);

  const navigate = useNavigate();

  useEffect(() => {
    if (loginUser) {
      navigate('/main');
    }
  }, []);

  return (
    <div style={{ height: '100vh' }}>
      <img src={logo} style={{ display: 'block', margin: 'auto' }} />

      <div>
        <a href={KAKAO}>
          <img src={kakao_medium} style={{ display: 'block', margin: 'auto' }} />
        </a>
      </div>
    </div>
  );
}

export default Login;
