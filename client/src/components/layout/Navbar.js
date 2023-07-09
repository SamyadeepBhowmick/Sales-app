import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  function logout() {
    localStorage.removeItem("token");
  }

  return (
    <nav className="navbar bg-dark">
      <h1>
        <Link to="/">Instacart</Link>
      </h1>
      <ul>
        <li>
          <Link to="/register">Register</Link>
        </li>
        <li>
          <Link to="/" onClick={logout}>
            Logout
          </Link>
        </li>
      </ul>
    </nav>
  );
};
export default Navbar;
