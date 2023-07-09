import React from "react";
import { useEffect } from "react";
import { Fragment, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { transacList } from "../../reducers/transacSlice";
import { useDispatch, useSelector } from "react-redux";

const Transaction = () => {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const transactions = useSelector(
    (state) => state.transacDetails.transactions
  );

  const token = "Bearer " + localStorage.getItem("token");
  useEffect(() => {
    getAllTransactionFromStore(token);
  }, []);

  async function getAllTransactionFromStore(token) {
    try {
      const response = await axios({
        method: "get",
        url: "/api2/alltransactions",
        headers: {
          Authorization: token,
        },
      });

      dispatch(transacList(response.data));
    } catch (error) {
      console.log(error.response.data);
    }
  }

  return (
    <Fragment>
      <h1 className="large text-primary">All Transactions</h1>
      <div className="row">
        <div className="column">
          <p id="heading">Item List</p>
        </div>
        <div className="column">
          <p id="heading">Date of Purchase</p>
        </div>
        <div className="column">
          <p id="heading">Total Price</p>
        </div>
      </div>

      <div className="posts">
        {transactions === "No Transactions Found" || transactions === "" ? (
          <span>No Transactions Found</span>
        ) : (
          transactions.map((transac) => {
            return (
              <Fragment>
                <div className="row">
                  <div className="column">
                    <div>
                      {transac.items.map((eachItem) => {
                        return <p>{eachItem}</p>;
                      })}
                    </div>
                  </div>
                  <div className="column">
                    <p>{transac.dateOfPurchase}</p>
                  </div>
                  <div className="column">
                    <p>{transac.totalPrice}</p>
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
export default Transaction;
