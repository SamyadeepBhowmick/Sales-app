import { configureStore } from "@reduxjs/toolkit";
import alertReducer from "./reducers/alertSlice";
import authReducer from "./reducers/authSlice";
import itemReducer from "./reducers/itemSlice";
import cartReducer from "./reducers/cartSlice";
import transacReducer from "./reducers/transacSlice";

const store = configureStore({
  reducer: {
    alert: alertReducer,
    auth: authReducer,
    itemDetails: itemReducer,
    cartDetails: cartReducer,
    transacDetails: transacReducer,
  },
});
export default store;
