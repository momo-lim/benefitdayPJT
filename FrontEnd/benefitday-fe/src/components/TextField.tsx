import React from 'react';

const textFieldStyled = {
  display: 'flex',
  borderStyle: 'solid',
  borderWidth: '2px',
  padding: '12px',
  textAlign: 'center' as const,
  backgroundColor: 'white',
  borderRadius: '10px',
  gap: '8px',
};

interface propsType {
  contents: string;
}

export const TextField = (props: propsType) => {
  return (
    <div className="text" style={textFieldStyled}>
      {props.contents}
    </div>
  );
};
