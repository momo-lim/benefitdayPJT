/* eslint-disable @typescript-eslint/no-var-requires */
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router';
import styled from 'styled-components';
import ModalDetail from '../components/ModalDetail';

interface ResponseData {
  serviceList: Array<ResponseItem>;
}

interface ResponseItem {
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
  조회수: number;
  지원내용: string;
  지원대상: string;
  지원유형: string;
}
//----------------css---------------
const DivStyeld = styled.div`
  background-color: white;

  box-shadow: 0 5px 10px rgb(177, 177, 177);
  padding: 10px;
  &:hover {
    background-color: #eeeeee;
    cursor: pointer;
  }
`;

//----------------css---------------

function SupportList() {
  const { type } = useParams();
  const value = localStorage.getItem('accessToken') as string;
  const [supportTypeData, setSupportTypeData] = useState<Array<ResponseItem>>([]);
  const [open, setOpen] = useState(false);
  const [id, setId] = useState<number>();
  if (type) {
    useEffect(() => {
      axios
        .get<ResponseData>(`/service/type/${type}`, {
          headers: {
            accessToken: `${value}`,
          },
        })
        .then((res) => {
          setSupportTypeData(res.data.serviceList);
        })
        .catch((err) => console.log(err));
    }, []);
  }

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
  return (
    <div style={{ width: '700px', margin: 'auto', minHeight: '100vh' }}>
      {supportTypeData.map((item, index) => (
        <DivStyeld key={index} onClick={() => OpenModal(item.id)}>
          <p style={{ paddingLeft: '10px' }}>
            <img
              style={{ width: '100px', paddingRight: '20px' }}
              src={require(`../images/local/${item.소관기관명.split(' ')[0]}.jpg`) as string}
              alt=""
            />

            {item.서비스명}
          </p>
          <span style={{ padding: '10px', color: '#46a7a1' }}>{item.소관기관명}</span>
        </DivStyeld>
      ))}

      {open && <ModalDetail modalClose={CloseModal} idData={id}></ModalDetail>}
    </div>
  );
}

export default SupportList;
