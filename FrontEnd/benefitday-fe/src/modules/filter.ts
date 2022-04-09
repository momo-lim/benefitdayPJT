import { createSlice, PayloadAction } from '@reduxjs/toolkit';

const initialState = {
  province: '',
  city: '',
  income: '',
  personal: '',
  family: '',
  birthday: '',
};

export const filterSlice = createSlice({
  name: 'filter',
  initialState,
  reducers: {
    setProvince: (state, action: PayloadAction<string>) => {
      state.province = action.payload;
    },
    setCity: (state, action: PayloadAction<string>) => {
      state.city = action.payload;
    },
    setIncome: (state, action: PayloadAction<string>) => {
      state.income = action.payload;
    },
    setPersonal: (state, action: PayloadAction<string>) => {
      state.personal = action.payload;
    },
    setFamily: (state, action: PayloadAction<string>) => {
      state.family = action.payload;
    },
    setBirthday: (state, action: PayloadAction<string>) => {
      state.birthday = action.payload;
    },
  },
});

export const { setProvince, setCity, setIncome, setPersonal, setFamily, setBirthday } = filterSlice.actions;
export default filterSlice.reducer;
