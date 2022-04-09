import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../app/store';
import { addContents } from '../modules/proposal';

const qnaStyled = {
  display: 'flex',
  flexDirection: 'column' as const,
  width: '100%',
  padding: '36px',
  gap: '16px',
};

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

interface FAQType {
  id: number;
  question: string;
  answer: string;
}

const FAQItem = (props: FAQType) => {
  const { question, answer } = props;
  const [open, setOpen] = useState(false);
  return (
    <React.Fragment>
      <div
        className="question"
        style={textFieldStyled}
        onClick={(e) => {
          setOpen((open) => !open);
        }}
      >
        {`Q: ${question}`}
      </div>
      {open ? (
        <div className="answer" style={textFieldStyled}>
          {`A: ${answer}`}
        </div>
      ) : (
        ''
      )}
    </React.Fragment>
  );
};

function FAQ() {
  const contents = useSelector((state: RootState) => state.faq.faq_list);

  return (
    <React.Fragment>
      <div className="qna" style={qnaStyled}>
        {contents
          ? contents.map((item, index) => (
              <FAQItem key={index} id={item.id} question={item.question} answer={item.answer} />
            ))
          : ''}
      </div>
    </React.Fragment>
  );
}

export default FAQ;
