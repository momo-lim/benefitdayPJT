import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
interface Item {
  item: {
    id?: number;
    구비서류?: string;
    도특별시광역시?: string;
    부서명?: string;
    서비스Id?: string;
    서비스명?: string;
    서비스목적?: string;
    선정기준?: string;
    소관기관명?: string;
    시군구?: string;
    신청기한?: string;
    신청방법?: string;
    접수기관명?: string;
    지원내용?: string;
    지원대상?: string;
    지원유형?: string;
  };
}

// ----------css------------
const StyleDiv = styled.div`
  border: solid 10px #46a7a1;
  background-color: white;
  padding: 5px;
  width: 192px;
  height: 270px;
  box-shadow: 0 5px 10px rgb(177, 177, 177);
  border-radius: 40px;
  white-space: normal;
  overflow: hidden;
  text-overflow: ellipsis;
  transition: 0.5s;
  margin: 20px;
  &:hover {
    /* transition: 0.5s; */
    transform: scale(1.1);
    cursor: pointer;
  }
`;

const StyleHr = styled.hr`
  width: 80%;
`;
// ----------css------------

function RecommendCard({ item }: Item) {
  const [support, setSupport] = useState('');
  const [support2, setSupport2] = useState('');
  // const [open, setOpen] = useState(false);

  useEffect(() => {
    const 지원내용 = item.지원내용?.split('\r\n')[0];
    const 신청방법 = item.신청방법?.split('\r\n')[0];
    if (지원내용 && 신청방법) {
      setSupport(지원내용);
      setSupport2(신청방법);
    }
  }, []);

  return (
    <div>
      <StyleDiv>
        <h3>{item.서비스명}</h3>
        <StyleHr></StyleHr>
        <p>{support}</p>
        <p>{support2}</p>
      </StyleDiv>
    </div>
  );
}

export default RecommendCard;
