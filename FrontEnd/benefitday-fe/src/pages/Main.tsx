import axios from 'axios';
import React, { useEffect, useState } from 'react';
import RecommendCard from '../components/RecommendCard';
import SupportType from '../components/SupportType';
import { useNavigate } from 'react-router';
import logo from '../images/logo.gif';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import ModalDetail from '../components/ModalDetail';
import styled from 'styled-components';
import SearchIcon from '@mui/icons-material/Search';

// ----------------type------------------------
interface ResponseData {
  serviceList: Array<ResponseItem>;
}

interface ResponseItem {
  serviceId: number;
  id: number;
  구비서류: string;
  도특별시광역시: string;
  부서명: string;
  서비스Id: string;
  서비스명: string;
  서비스목적: string;
  선정기준: string;
  소관기관명: string;
  시군구: string;
  신청기한: string;
  신청방법: string;
  접수기관명: string;
  지원내용: string;
  지원대상: string;
  지원유형: string;
}

// ----------------type------------------------

// ----------------css-------------------------
const recommendDiv = {
  // display: 'flex',
  gap: '20px',
  marginTop: '20px',
};

const imgDiv = {
  display: 'flex',
  flexWrap: 'wrap',
  justifyContent: 'space-between',
  gap: '10px',
} as const;

const StyledSlider = styled(Slider)`
  .slick-prev {
    background-color: gray;
    border-radius: 10px;
  }
  .slick-next {
    background-color: gray;
    border-radius: 10px;
  }
`;

const searchBar = {
  display: 'flex',
  justifyContent: 'center',
  alignItems: 'center',
  backgroundColor: '#46a7a1',
  height: '50px',
  width: '70%',
  margin: 'auto',
  cursor: 'pointer',
  borderRadius: '10px',
};
// ----------------css-------------------------

function Main() {
  const [recommend, setRecommend] = useState<Array<ResponseItem>>([]);
  const [recommend2, setRecommend2] = useState<Array<ResponseItem>>([]);

  const navigate = useNavigate();

  // 목록 불러오기
  useEffect(() => {
    // 로그인 안하고 다른 경로 들어가면 login으로 보내버림
    const value = localStorage.getItem('accessToken');
    if (!value) {
      navigate('/');
    }

    if (value) {
      axios
        .get<ResponseData>('/service/recommend', {
          headers: {
            accessToken: `${value}`,
          },
        })
        .then((res) => {
          setRecommend(res.data.serviceList);
        })
        .catch((err) => console.log(err));
    }
    axios
      .get<ResponseData>('/service/views')
      .then((res) => {
        setRecommend2(res.data.serviceList);
      })
      .catch((err) => console.log(err));
  }, []);

  const settings = {
    dots: false,
    infinite: false,
    speed: 500,
    slidesToShow: 4,
    slidesToScroll: 4,
  };
  const [open, setOpen] = useState(false);
  const [id, setId] = useState<number>();
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

  const moveSearch = () => {
    navigate('/searchlist');
  };

  return (
    <div>
      <img src={logo} style={{ display: 'block', margin: 'auto' }} />

      <div style={searchBar} onClick={moveSearch}>
        <h2 style={{ color: 'white' }}>다른정책 찾아보기</h2>
        <SearchIcon style={{ color: 'white', padding: '10px' }}></SearchIcon>
      </div>

      <h2>추천 정책 지원형태 별로 보기</h2>
      <div style={imgDiv}>
        <SupportType imgText="현금"></SupportType>
        <SupportType imgText="현물"></SupportType>
        <SupportType imgText="서비스"></SupportType>
        <SupportType imgText="법률지원"></SupportType>
        <SupportType imgText="의료지원"></SupportType>
        <SupportType imgText="문화여가생활"></SupportType>
      </div>
      <br />
      <br />
      <br />
      <h2>회원의 추천 정책</h2>
      <div style={recommendDiv}>
        <StyledSlider {...settings}>
          {recommend
            ? recommend.map((item, i: number) => (
                <div onClick={() => OpenModal(item.id)} key={i}>
                  <RecommendCard item={item}></RecommendCard>
                </div>
              ))
            : ''}
        </StyledSlider>
      </div>
      <br />
      <br />
      <br />
      <h2>다른사람들이 많이 찾아본 정책</h2>
      <div style={recommendDiv}>
        <StyledSlider {...settings}>
          {recommend2
            ? recommend2.map((item, i: number) => (
                <div onClick={() => OpenModal(item.id)} key={i}>
                  <RecommendCard item={item}></RecommendCard>
                </div>
              ))
            : ''}
        </StyledSlider>
      </div>
      {open && <ModalDetail modalClose={CloseModal} idData={id}></ModalDetail>}
    </div>
  );
}

export default Main;
