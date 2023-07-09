import { createSlice } from "@reduxjs/toolkit";

// Slice

const slice = createSlice({
  name: "transacDetails",
  initialState: {
    transactions: "",
    isLoading: true,
  },
  reducers: {
    transacList: (state, action) => {
      state.transactions = action.payload;
      state.isLoading = false;
    },
  },
});

export const { transacList } = slice.actions;

export default slice.reducer;
