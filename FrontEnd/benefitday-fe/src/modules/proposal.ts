import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface QnA {
  id: number;
  email: string;
  serviceId: number;
  title: string;
  contents: string;
}

const initialState = {
  qna_list: [] as QnA[],
};

export const proposalSlice = createSlice({
  name: 'proposal',
  initialState,
  reducers: {
    initQnA: (state, action: PayloadAction<QnA[]>) => {
      state.qna_list = action.payload;
    },
    addContents: {
      reducer: (state, action: PayloadAction<QnA>) => {
        console.log('1');
        state.qna_list.push(action.payload);
      },
      prepare: (id: number, email: string, serviceId: number, title: string, contents: string) => {
        console.log('2');
        return { payload: { id, email, serviceId, title, contents } };
      },
    },
  },
});

export const { initQnA, addContents } = proposalSlice.actions;
export default proposalSlice.reducer;
