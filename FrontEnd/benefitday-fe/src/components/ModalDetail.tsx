/* eslint-disable @typescript-eslint/no-unsafe-call */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import ModalComment from './ModalComment';
import ModalLike from './ModalLike';

interface ModalProps {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  modalClose: (e: any) => void; // 아무것도 리턴하지 않는다는 함수를 의미합니다.
  idData?: number;
}
interface ResponseData {
  service: object;
}

interface state {
  id?: number;
  구비서류?: string;
  도특별시광역시?: string;
  부서명?: string;
  서비스Id?: number;
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
}

// ----------css-----------
const ModalStyled = {
  // fixed 하고 top 0 을 하면 top과의 거리차이가 0이 된다. 모두 0하면 전체 화면이 꽉차게됨
  position: 'fixed' as const,
  top: '0',
  left: '0',
  right: '0',
  bottom: '0',
  backgroundColor: 'rgba(15, 15, 15, 0.79)',
  zIndex: '2',
  transition: '.3s linear',
};

const ModalStyled2 = {
  position: 'absolute',
  width: '640px',
  height: '90vh',
  left: '50%',
  top: '50%',
  zIndex: '3',
  backgroundColor: '#fff',
  transform: 'translate(-50%, -50%)',
  transition: '.3s linear',
  wordWrap: 'break-word',
  overflowY: 'auto',
  borderRadius: '20px',
} as const;

const BoxStyled = {
  padding: '10px',
  boxShadow: '0 5px 10px rgb(177, 177, 177)',
  borderRadius: '20px',
};

const ButtonStyled = {
  width: '90%',
  backgroundColor: '#46a7a1',
  textAlign: 'center',
  padding: '10px',
  margin: 'auto',
  borderRadius: '20px',
} as const;

const AStyled = {
  textDecoration: 'none',
  color: 'white',
  display: 'inline-block',
  width: '100%',
  height: '23px',
};

// ----------css-----------

function ModalDetail({ modalClose, idData }: ModalProps) {
  let myIdData: number;
  if (idData) {
    myIdData = idData;
  }

  const [detailInfo, setDetailInfo] = useState<state>();
  const value = localStorage.getItem('accessToken') as string;
  useEffect(() => {
    axios
      .get<ResponseData>(`/service/read/${myIdData}`, {
        headers: {
          accessToken: value,
        },
      })
      .then((res) => {
        setDetailInfo(res.data.service);
      })

      .catch((err) => console.log(err));
  }, []);

  return (
    <div style={ModalStyled} onClick={(e) => modalClose(e)} id="in">
      <div style={ModalStyled2}>
        {detailInfo && (
          <div>
            <div style={{ width: '100%', height: '40px', backgroundColor: '#46a7a1' }}></div>
            <div style={{ padding: '20px' }}>
              <div style={BoxStyled}>
                <h1>{detailInfo.서비스명}</h1>
                <p style={{ whiteSpace: 'pre-line' }}>{detailInfo.서비스목적}</p>
                <p>신청기한 : {detailInfo.신청기한}</p>
                <p>소관기관명 : {detailInfo.소관기관명}</p>
                {detailInfo.서비스Id && (
                  <div style={ButtonStyled}>
                    <a style={AStyled} href={`https://www.gov.kr/portal/rcvfvrSvc/dtlEx/${detailInfo.서비스Id}`}>
                      신청하기
                    </a>
                  </div>
                )}
              </div>
              <br />
              <hr />
              <br />
              <div style={BoxStyled}>
                <h3 style={{ textAlign: 'center', color: '#46a7a1' }}>누가 받나요?</h3>
                <p style={{ whiteSpace: 'pre-line' }}>{detailInfo.지원대상}</p>
                <p style={{ whiteSpace: 'pre-line' }}>{detailInfo.선정기준}</p>
              </div>
              <br />
              <div style={BoxStyled}>
                <h3 style={{ textAlign: 'center', color: '#46a7a1' }}>무엇을 받나요?</h3>
                <p style={{ whiteSpace: 'pre-line' }}>{detailInfo.지원내용}</p>
              </div>
              <br />

              <div style={BoxStyled}>
                <h3 style={{ textAlign: 'center', color: '#46a7a1' }}>정책이 도움이 되었나요?</h3>

                {detailInfo.서비스명 ? (
                  idData ? (
                    <ModalLike detailInfo={detailInfo.서비스명} myIdData={idData}></ModalLike>
                  ) : (
                    ''
                  )
                ) : (
                  ''
                )}
              </div>
              <div style={BoxStyled}>
                <h3 style={{ textAlign: 'center', color: '#46a7a1' }}>의견을 남겨주세요!</h3>
                {idData && <ModalComment myIdData={idData}></ModalComment>}
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default ModalDetail;
