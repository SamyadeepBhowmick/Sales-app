import { createSlice } from "@reduxjs/toolkit";

// Slice

const slice = createSlice({
  name: "alert",
  initialState: {
    msg: "",
    isLoading: true,
  },
  reducers: {
    setAlert: (state, action) => {
      state.msg = action.payload;
      state.isLoading = false;
    },
    removeAlert: (state) => {
      state.msg = "FAIL";
      state.isLoading = false;
    },
  },
});

export const { setAlert, removeAlert } = slice.actions;

export default slice.reducer;
