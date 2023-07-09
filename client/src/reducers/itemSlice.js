import { createSlice } from "@reduxjs/toolkit";

// Slice

const slice = createSlice({
  name: "itemDetails",
  initialState: {
    items: "",
    isLoading: true,
  },
  reducers: {
    itemList: (state, action) => {
      state.items = action.payload;
      state.isLoading = false;
    },
  },
});

export const { itemList } = slice.actions;

export default slice.reducer;
