import { createSlice, PayloadAction } from '@reduxjs/toolkit';

interface user {
  email: string;
  profileImageUrl: string;
  nickname: string;
}

const initialState = {
  isLogin: false,
  userInfo: {
    email: '',
    profileImageUrl: '',
    nickname: '',
  },
};

export const userSlice = createSlice({
  name: 'user', // 액션타입의 이름이 중복되는것을 막기위한 네임값
  initialState, // 리듀서에서 사용 되는 initialState
  reducers: {
    login: (state, action: PayloadAction<user>) => {
      state.isLogin = true;
      state.userInfo = action.payload;
    },
    logout: (state) => {
      state.isLogin = false;
      state.userInfo = {
        email: '',
        profileImageUrl: '',
        nickname: '',
      };
    },
  },
});

export const { login, logout } = userSlice.actions;
export default userSlice.reducer;
