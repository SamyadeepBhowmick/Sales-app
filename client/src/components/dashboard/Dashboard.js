import React from "react";
import { useEffect } from "react";
import { Fragment, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { itemList } from "../../reducers/itemSlice";
import { useDispatch, useSelector } from "react-redux";

const Dashboard = () => {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const allItems = useSelector((state) => state.itemDetails.items);

  const token = "Bearer " + localStorage.getItem("token");
  useEffect(() => {
    getAllItemsFromStore(token);
  }, []);

  async function getAllItemsFromStore(token) {
    try {
      const response = await axios({
        method: "get",
        url: "/api2/allitems",
        headers: {
          Authorization: token,
        },
      });

      dispatch(itemList(response.data));
    } catch (error) {
      console.log(error.response.data);
    }
  }

  async function sayHello(id) {
    try {
      const response = await axios({
        method: "post",
        url: "/api2/addtocart/" + id + "/" + "1",
        headers: {
          Authorization: token,
          "Content-Type": "application/json",
        },
      });

      getAllItemsFromStore(token);
    } catch (err) {
      console.error(err);
    }
  }

  const routeChange = () => {
    let path = `/cart`;
    navigate(path);
  };

  return (
    <Fragment>
      <h1 className="large text-primary">Exclusive Items</h1>
      <div className="row">
        <button type="button" className="btn btn-light" onClick={routeChange}>
          Cart
          <i className="fas fa-cart-plus"></i>
        </button>
      </div>
      <div className="row">
        <div className="column">
          <p id="heading">Product</p>
        </div>
        <div className="column">
          <p id="heading">Per Product Price</p>
        </div>
        <div className="column">
          <p id="heading">Available Stock</p>
        </div>
        <div className="column">
          <p id="heading">Add To Cart</p>
        </div>
      </div>

      <div className="posts">
        {allItems === "No items Found" || allItems === "" ? (
          <span>No items Found</span>
        ) : (
          allItems.map((item) => {
            return (
              <Fragment>
                <div className="row">
                  <div className="column">
                    <p>{item.itemName}</p>
                  </div>
                  <div className="column">
                    <p>{item.price}</p>
                  </div>
                  <div className="column">
                    <p>{item.quantity}</p>
                  </div>
                  <div className="column">
                    <button
                      type="button"
                      className="btn btn-light"
                      onClick={() => sayHello(item.id)}
                    >
                      <i className="fas fa-cart-plus"></i>
                    </button>
                    {/* </p> */}
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
export default Dashboard;
