import React, { Fragment, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import Landing from "./components/layout/Landing";
import Navbar from "./components/layout/Navbar";
import Register from "./components/auth/Register";
import Login from "./components/auth/Login";
import Dashboard from "./components/dashboard/Dashboard";
import Cart from "./components/dashboard/Cart";
import Transaction from "./components/dashboard/Transaction";
import "./App.css";

const App = () => {
  return (
    <Fragment>
      <Navbar />
      <Routes>
        <Route path="/" element={<Landing />}></Route>
      </Routes>
      <section className="container">
        <Routes>
          <Route path="/register" element={<Register />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/dashboard" element={<Dashboard />}></Route>
          <Route path="/cart" element={<Cart />}></Route>
          <Route path="/transaction" element={<Transaction />}></Route>
        </Routes>
      </section>
    </Fragment>
  );
};

export default App;
