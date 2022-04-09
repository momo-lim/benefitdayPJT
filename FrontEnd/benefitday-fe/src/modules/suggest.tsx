import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface Suggestion {
  id: number;
  email: string;
  title: string;
  contents: string;
}

const initialState = {
  suggestion_list: [] as Suggestion[],
};

export const suggestionSlice = createSlice({
  name: 'suggest',
  initialState,
  reducers: {
    initSuggestion: (state, action: PayloadAction<Suggestion[]>) => {
      state.suggestion_list = action.payload;
    },
  },
});

export const { initSuggestion } = suggestionSlice.actions;
export default suggestionSlice.reducer;
