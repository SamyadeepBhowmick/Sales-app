import React from "react";
import { useEffect } from "react";
import { Fragment, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { setAlert } from "../../reducers/alertSlice";
import authSlice, { loginUser } from "../../reducers/authSlice";
import { useDispatch, useSelector } from "react-redux";

const Login = () => {
  let navigate = useNavigate();
  const dispatch = useDispatch();
  const token = "Bearer " + localStorage.getItem("token");
  useEffect(() => {
    validate(token);
  }, []);

  async function validate(token) {
    try {
      const response = await axios({
        method: "get",
        url: "/validate",
        headers: {
          Authorization: token,
        },
      });

      console.log(response.data);

      if (response.data.valid) {
        localStorage.setItem("email", response.data.email);
        console.log(response.data.email);
        navigate("/dashboard");
      }
    } catch (error) {
      console.log(error.response.data);
    }
  }

  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const { email, password } = formData;

  const onChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    const newUser = {
      email,
      password,
    };
    try {
      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };
      const body = JSON.stringify(newUser);

      const res = await axios.post("/login", body, config);
      dispatch(loginUser(res.data));
      dispatch(setAlert("login success"));
      console.log(res.data);
      localStorage.setItem("token", res.data);
      navigate("/dashboard");
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Fragment>
      <h1 className="large text-primary">Sign In</h1>
      <p className="lead">
        <i className="fas fa-user"></i> Sign into Your Account
      </p>
      <form className="form" onSubmit={(e) => onSubmit(e)}>
        <div className="form-group">
          <input
            type="email"
            placeholder="Email Address"
            name="email"
            value={email}
            onChange={(e) => onChange(e)}
            required
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            placeholder="Password"
            name="password"
            value={password}
            onChange={(e) => onChange(e)}
          />
        </div>
        <input type="submit" className="btn btn-primary" value="Login" />
      </form>
      <p className="my-1">
        Don't have an account? <Link to="/register">Sign Up</Link>
      </p>
    </Fragment>
  );
};
export default Login;
