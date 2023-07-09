import { createSlice } from "@reduxjs/toolkit";

// Slice

const slice = createSlice({
  name: "auth",
  initialState: {
    msg: "",
    token: "",
    isAuthenticated: false,
    isLoading: true,
  },
  reducers: {
    registerUser: (state, action) => {
      state.msg = action.payload;
      state.isLoading = false;
    },
    loginUser: (state, action) => {
      state.msg = "Login Success";
      state.token = action.payload;
      state.isAuthenticated = true;
      state.isLoading = false;
    },
  },
});

export const { registerUser, loginUser } = slice.actions;

export default slice.reducer;
