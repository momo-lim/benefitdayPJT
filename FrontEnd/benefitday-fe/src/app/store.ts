import { configureStore } from '@reduxjs/toolkit';
import { combineReducers } from 'redux';
import userReducer from '../modules/user';
import filterReducer from '../modules/filter';
import proposalReducer from '../modules/proposal';
import faqReducer from '../modules/faq';
import suggestionReducer from '../modules/suggest';

import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import thunk from 'redux-thunk';

const reducers = combineReducers({
  users: userReducer,
  filter: filterReducer,
  proposal: proposalReducer,
  faq: faqReducer,
  suggestion: suggestionReducer,
});

const persistConfig = {
  key: 'root',
  storage: storage,
};

const persistedReducer = persistReducer(persistConfig, reducers);

export const store = configureStore({
  reducer: persistedReducer,
  devTools: process.env.NODE_ENV !== 'production',
  middleware: [thunk],
});

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>;
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch;
