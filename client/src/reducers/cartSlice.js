import { createSlice } from "@reduxjs/toolkit";

// Slice

const slice = createSlice({
  name: "cartDetails",
  initialState: {
    cartItems: "",
    isLoading: true,
  },
  reducers: {
    cartList: (state, action) => {
      state.cartItems = action.payload;
      state.isLoading = false;
    },
  },
});

export const { cartList } = slice.actions;

export default slice.reducer;
