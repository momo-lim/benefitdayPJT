import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface FAQ {
  id: number;
  question: string;
  answer: string;
}

const initialState = {
  faq_list: [] as FAQ[],
};

export const faqSlice = createSlice({
  name: 'faq',
  initialState,
  reducers: {
    initFAQ: (state, action: PayloadAction<FAQ[]>) => {
      state.faq_list = action.payload;
    },
  },
});

export const { initFAQ } = faqSlice.actions;
export default faqSlice.reducer;
