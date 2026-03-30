import { useState } from "react";

const API = import.meta.env.VITE_API_URL || "http://localhost:8080";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const submit = async (e) => {
    e.preventDefault();
    const res = await fetch(`${API}/api/auth/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password })
    });
    const data = await res.json();
    if (res.ok) {
      localStorage.setItem("token", data.token);
      alert("Logged in");
    } else {
      alert(data.message || "Login failed");
    }
  };

  return (
    <form onSubmit={submit}>
      <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" />
      <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" placeholder="Password" />
      <button type="submit">Login</button>
    </form>
  );
}
