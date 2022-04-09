import React from 'react';
import { useDispatch } from 'react-redux';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router';
import { logout } from '../modules/user';
import styled from 'styled-components';

// ---------------css-------------

const navBarStyled = {
  display: 'flex',
  justifyContent: 'space-around',
  backgroundColor: '#46a7a1',
  paddingTop: '15px',
  paddingBottom: '15px',
  height: '24px',
};

const linkStyled = {
  textDecoration: 'none',
  color: 'white',
  padding: '30px',
  fontSize: '19px',
};

const ButtonStyled = styled.button`
  &:hover {
    cursor: pointer;
  }
  background: none;
  border: none;
  color: white;
  font-size: 19px;
`;
// ---------------css-------------
function Nav() {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 로그아웃
  const onLogout = () => {
    dispatch(logout());
    localStorage.removeItem('accessToken');
    navigate('');
  };

  const value = localStorage.getItem('accessToken');

  return (
    <div style={navBarStyled}>
      {value && (
        <div>
          <Link to="/main" style={linkStyled}>
            메인페이지
          </Link>
          <Link to="/policy" style={linkStyled}>
            정책게시판
          </Link>
        </div>
      )}
      {value && (
        <div>
          <ButtonStyled onClick={onLogout}>로그아웃</ButtonStyled>

          <Link to="/profile" style={linkStyled}>
            마이페이지
          </Link>
        </div>
      )}
    </div>
  );
}

export default Nav;
