import Income from "./pages/Income.jsx";
import Home from "./pages/Home.jsx";
import Filter from "./pages/Filter.jsx";
import Category from "./pages/Category.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/Signup.jsx";
import Expense from "./pages/Expense.jsx";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Toaster } from "react-hot-toast";
const App = () => {
  return (
    <>
      <Toaster />
      <Routes>
        <Route path="/dashboard" element={<Home />}></Route>
        <Route path="/income" element={<Income />}></Route>
        <Route path="/expense" element={<Expense />}></Route>
        <Route path="/filter" element={<Filter />}></Route>
        <Route path="/category" element={<Category />}></Route>
        <Route path="/login" element={<Login />}></Route>
        <Route path="/signup" element={<Signup />}></Route>
      </Routes>
    </>
  );
};
export default App;
