import { useState } from "react";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";

export default function App() {
  const [tab, setTab] = useState("login");
  return (
    <div style={{ padding: 24 }}>
      <h1>Student Management</h1>
      <button type="button" onClick={() => setTab("login")}>Login</button>
      <button type="button" onClick={() => setTab("register")} style={{ marginLeft: 8 }}>Register</button>
      <div style={{ marginTop: 16 }}>{tab === "login" ? <Login /> : <Register />}</div>
    </div>
  );
}
