import React from "react";
import { useEffect } from "react";
import { Fragment, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { cartList } from "../../reducers/cartSlice";
import { useDispatch, useSelector } from "react-redux";

const Cart = () => {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const allCartItems = useSelector((state) => state.cartDetails.cartItems);
  const token = "Bearer " + localStorage.getItem("token");
  useEffect(() => {
    getAllCartItemsFromStore(token);
  }, []);

  async function getAllCartItemsFromStore(token) {
    try {
      const response = await axios({
        method: "get",
        url: "/api2/allcartitems",
        headers: {
          Authorization: token,
        },
      });

      dispatch(cartList(response.data));
    } catch (error) {
      console.log(error.response.data);
    }
  }

  async function sayHello(id) {
    try {
      const response = await axios({
        method: "delete",
        url: "/api2/removefromcart/" + id,
        headers: {
          Authorization: token,
        },
      });

      getAllCartItemsFromStore(token);
    } catch (err) {
      console.error(err);
    }
  }

  async function placeOrder() {
    try {
      const response = await axios({
        method: "post",
        url: "/api2/placeorder",
        headers: {
          Authorization: token,
        },
      });

      getAllCartItemsFromStore(token);
      let path = `/transaction`;
      navigate(path);
    } catch (err) {
      console.error(err);
    }
  }

  const routeChange = () => {
    let path = `/transaction`;
    navigate(path);
  };

  return (
    <Fragment>
      <h1 className="large text-primary">All Cart Items</h1>
      <div className="row">
        <div className="column">
          <button type="button" className="btn btn-light" onClick={placeOrder}>
            Place Order
          </button>
        </div>
        <div className="column">
          <button type="button" className="btn btn-light" onClick={routeChange}>
            See All Transactions
          </button>
        </div>
      </div>
      <div className="row">
        <div className="column">
          <p id="heading">Product</p>
        </div>
        <div className="column">
          <p id="heading">Quantity</p>
        </div>
        <div className="column">
          <p id="heading">Total Price</p>
        </div>
        <div className="column">
          <p id="heading">Remove Item From Cart</p>
        </div>
      </div>

      <div className="posts">
        {allCartItems === "Cart is empty" || allCartItems === "" ? (
          <span>Cart is empty</span>
        ) : (
          allCartItems.map((cartItem) => {
            return (
              <Fragment>
                <div className="row">
                  <div className="column">
                    <p>{cartItem.itemName}</p>
                  </div>
                  <div className="column">
                    <p>{cartItem.quantity}</p>
                  </div>
                  <div className="column">
                    <p>{cartItem.price}</p>
                  </div>
                  <div className="column">
                    <button
                      type="button"
                      className="btn btn-light"
                      onClick={() => sayHello(cartItem.id)}
                    >
                      Remove
                    </button>
                  </div>
                </div>
              </Fragment>
            );
          })
        )}
      </div>
    </Fragment>
  );
};
export default Cart;
