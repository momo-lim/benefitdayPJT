import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../app/store';
import { useNavigate } from 'react-router';
import { Stack, Button, Box } from '@mui/material';
import logo from '../images/logo.gif';
import FAQ from './Faq';
import Help from './help';
import Suggestion from './Suggestion';

const divStyled = {
  display: 'flex',
  flexDirection: 'column' as const,
  justifyContent: 'center',
};

const PageNavigator = () => {
  const [faq, setFaq] = useState(true);
  const [qna, setQna] = useState(false);
  const [suggest, setSuggest] = useState(false);

  return (
    <React.Fragment>
      <Box display="flex" justifyContent="center" flexDirection="column" alignItems="center">
        {faq ? <h1 style={{ textAlign: 'center' }}>FAQ</h1> : ''}
        {qna ? <h1 style={{ textAlign: 'center' }}>1:1 문의</h1> : ''}
        {suggest ? <h1 style={{ textAlign: 'center' }}>제안하기</h1> : ''}
        <Stack spacing={10} direction="row" alignItems="center">
          <Button
            variant="text"
            onClick={() => {
              setFaq(true);
              setSuggest(false);
              setQna(false);
            }}
          >
            FAQ
          </Button>
          <Button
            onClick={() => {
              setFaq(false);
              setQna(true);
              setSuggest(false);
            }}
          >
            1:1 문의
          </Button>
          <Button
            onClick={() => {
              setFaq(false);
              setQna(false);
              setSuggest(true);
            }}
          >
            제안
          </Button>
        </Stack>
        {faq ? <FAQ /> : ''}
        {qna ? <Help /> : ''}
        {suggest ? <Suggestion /> : ''}
      </Box>
    </React.Fragment>
  );
};

function Policy() {
  const navigate = useNavigate();
  const value = localStorage.getItem('accessToken');
  useEffect(() => {
    if (!value) {
      navigate('/');
    }
  }, []);
  return (
    <div style={{ height: '100vh' }}>
      <img src={logo} style={{ display: 'block', margin: 'auto' }} onClick={() => navigate('/main')} />
      <PageNavigator />
    </div>
  );
}

export default Policy;
