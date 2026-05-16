import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { assets } from "../assets/assets.js";
import Input from "../components/Input.jsx";
import { validateEmail } from "../util/validation.js";
import axiosConfig from "../util/axiosConfig.jsx";
import { API_ENDPOINTS } from "../util/apiEndpoints.js";
import { toast } from "react-toastify";
const SignUp = () => {
  const [fullName, setFullName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [isloading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    //basic validation

    if (!fullName.trim()) {
      setError("Please enter your fullname");
      setIsLoading(false);
      return;
    }
    if (!validateEmail(email)) {
      setError("Please enter your email");
      setIsLoading(false);
      return;
    }
    if (!password.trim()) {
      setError("Please enter your password");
      setIsLoading(false);
      return;
    }

    setError("");

    //signup api call;

    try {
      const response = await axiosConfig.post(API_ENDPOINTS.REGISTER, {
        fullName,
        email,
        password,
      });
      if (response.status === 201) {
        toast.success("Profile created successfully");
        navigate("/login");
      }
    } catch (err) {
      console.error("Something went wrong", err);
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="h-screen w-full relative flex items-center justify-center overflow-hidden">
      <img
        src={assets.login_bg}
        alt="Background"
        className="absolute inset-0 w-full h-full object-cover filter blur-sm"
      ></img>
      <div className="relative z-10 w-full max-w-lg px-6 ">
        <div className="bg-white bg-opacity-95 backdrop-blur-sm rounded-lg shadow-2xl p-8 max-h-[90vh] overflow-y-auto">
          <h3 className="text-2xl font-semibold text-black text-center mb-2">
            Create An Account
          </h3>
          <p className="text-sm text-slate-700 text-center mb-8">
            Start tracking your spendings by joining with us
          </p>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div className="flex justify-center mb-6">
              {/* {profile image} */}
            </div>
            <div className="grid grid-cols-2 md:grid-cols-2 gap-4">
              <Input
                value={fullName}
                onChange={(e) => setFullName(e.target.value)}
                label="Full Name"
                placeholder="Jhone Doe"
                type="text"
              />
              <Input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                label="Email Address"
                placeholder="name@example.com"
                type="email"
              />
              <div className="col-span-2">
                <Input
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  label="Password"
                  placeholder="**********"
                  type="password"
                />
              </div>
            </div>
            {error && (
              <p className="text-red-800 text-sm text-center bg-red-50 p-2 rounded">
                {error}
              </p>
            )}
            <button
              disabled={isloading}
              className="w-full py-3 text-lg font-medium bg-purple-800 text-white rounded hover:bg-purple-400"
              type="submit"
            >
              {isloading ? <>Signing Up...</> : "SIGN UP"}
            </button>
            <p className="text-sm text-slate-800 text-center mt-6">
              Already have an account?
              <Link
                to="/login"
                className="font-medium text-primary underline hover:text-primary-dark transition-colors"
              >
                Login
              </Link>
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};
export default SignUp;
