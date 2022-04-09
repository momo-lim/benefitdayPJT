/* eslint-disable @typescript-eslint/no-var-requires */

import React from 'react';

import { useNavigate } from 'react-router';
import styled from 'styled-components';

// -------------type-------------
interface propsData {
  imgText: string;
}

// -------------type-------------

// -------------css-------------
const DivStyled = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 300px;
  height: 160px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 5px 10px rgb(177, 177, 177);
  transition: 0.5s;
  &:hover {
    transform: scale(1.1);
    cursor: pointer;
  }
`;

const imgStyled = {
  width: '100px',
};

// -------------css-------------

function SupportType({ imgText }: propsData) {
  const image = require(`../images/${imgText}.png`) as string;
  const navigate = useNavigate();

  const clickHandler = (imgText: string) => {
    navigate(`/supportlist/${imgText}`);
  };

  return (
    <DivStyled onClick={() => clickHandler(imgText)}>
      <img style={imgStyled} src={image} alt="" />
      <p>{imgText}</p>
    </DivStyled>
  );
}

export default SupportType;
