/* eslint-disable @typescript-eslint/no-var-requires */
import axios from 'axios';
import React, { useEffect, useState } from 'react';

import styled from 'styled-components';

import ModalDetail from '../components/ModalDetail';

interface getData {
  serviceList: Array<resultData>;
}

interface resultData {
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

const InputStyled = {
  border: '5px solid #46a7a1',
  padding: '10px',
  margin: '20px 20px 20px 240px',
};
//----------------css---------------

function SearchList() {
  const [searchValue, setSearchValue] = useState('');
  const [getResult, setGetResult] = useState<Array<resultData>>();
  const [open, setOpen] = useState(false);
  const [id, setId] = useState<number>();

  useEffect(() => {
    axios
      .get<getData>(`/service/search/${searchValue}`)
      .then((res) => {
        setGetResult(res.data.serviceList);
      })
      .catch((err) => console.log(err));
  }, [searchValue]);

  const saveSearchValue = (event: React.KeyboardEvent<HTMLInputElement>) => {
    const target = event.target as HTMLInputElement;
    if (event.key === 'Enter') {
      setSearchValue(target.value);
    }
  };

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
      <input style={InputStyled} onKeyPress={saveSearchValue} />

      {getResult &&
        getResult.map((item, index) => (
          <DivStyeld key={index} onClick={() => OpenModal(item.serviceId)}>
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

export default SearchList;
