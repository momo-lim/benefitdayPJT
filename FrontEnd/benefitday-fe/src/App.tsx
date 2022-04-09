import React, { useEffect } from 'react';
import { Route, Routes } from 'react-router';
import { useDispatch, useSelector } from 'react-redux';
import Nav from './pages/Nav';
import Main from './pages/Main';
import Policy from './pages/Policy';
import Login from './pages/Login';
import Profile from './pages/Profile';
import LoginKakao from './components/LoginKakao';
import Filter from './components/Filter';
import FAQ from './pages/Faq';
import SearchList from './pages/SearchList';
import SupportList from './pages/SupportList';
import Help from './pages/help';
import axios from 'axios';
import { RootState } from './app/store';
import { initFAQ } from './modules/faq';
import { initQnA } from './modules/proposal';
import { initSuggestion } from './modules/suggest';

interface FAQItem {
  id: number;
  question: string;
  answer: string;
}

interface QnAItem {
  id: number;
  email: string;
  serviceId: number;
  title: string;
  contents: string;
}

interface SuggestionItem {
  id: number;
  email: string;
  title: string;
  contents: string;
}

interface APIResponse {
  result: string;
  faqList: FAQItem[];
}

interface QnAResponse {
  result: string;
  inquiryList: QnAItem[];
}

interface SuggestionResponse {
  result: string;
  suggestionList: SuggestionItem[];
}

const AppStyled2 = {
  backgroundColor: '#E7F5F4',
  height: '100%',
  // position: 'fixed',
  // top: '0',
  // left: '0',
  // bottom: '0',
  // right: '0',
  // width: '100%',
  // backgroundSize: 'cover',
} as const;

const AppStyled = {
  // display: 'flex',
  // alignItems: 'flex-start',
  // justifyContent: 'center',
  // backgroundColor: '#E7F5F4',
  // height: '100vh',
  // width: '100%',
  maxWidth: '950px',
  margin: '0 auto',
  marginTop: '55px',
  gap: '16px',
};

const NavStyled = {
  position: 'fixed',
  top: '0',
  left: '0',
  right: '0',

  zIndex: '2',
} as const;

function App() {
  const faq_list = useSelector((state: RootState) => state.faq.faq_list);
  const qna_list = useSelector((state: RootState) => state.proposal.qna_list);
  const dispatch = useDispatch();

  useEffect(() => {
    const getFAQ = () => {
      axios
        .get<APIResponse>('/board/faq/list')
        .then((res) => {
          dispatch(initFAQ(res.data.faqList));
        })
        .catch((e) => {
          console.log(e);
        });
    };

    const getInquiry = () => {
      axios
        .get<QnAResponse>('/board/inquiry')
        .then((res) => {
          console.log(res);
          dispatch(initQnA(res.data.inquiryList));
        })
        .catch((e) => {
          console.log(e);
        });
    };

    const getSuggestion = () => {
      axios
        .get<SuggestionResponse>('/board/suggestion')
        .then((res) => {
          console.log(res);
          dispatch(initSuggestion(res.data.suggestionList));
        })
        .catch((e) => {
          console.log(e);
        });
    };

    console.log('init');
    getFAQ();
    getInquiry();
    getSuggestion();
  }, []);

  return (
    <div style={AppStyled2}>
      <div style={NavStyled}>
        <Nav></Nav>
      </div>
      <div style={AppStyled}>
        <Routes>
          <Route path="main" element={<Main />} />
          <Route path="policy" element={<Policy />} />
          <Route path="" element={<Login />} />
          <Route path="profile" element={<Profile />} />
          <Route path="filter" element={<Filter />} />
          <Route path="oauth" element={<LoginKakao />} />
          <Route path="faq" element={<FAQ />} />
          <Route path="searchlist" element={<SearchList />} />
          <Route path="supportlist/:type" element={<SupportList />} />
          <Route path="help" element={<Help />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;
