import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Login from "./components/Login";
import Register from "./components/Register";
import Landing from "./components/Landing";
import NotFound from "./components/Notfound";
import NewMember from "./components/NewMember";
import Edit from "./components/Edit";
import Detail from "./components/Detail";
import PrivateRoute from "./components/PrivateRoute";
import { GoogleOAuthProvider } from '@react-oauth/google';
import GoogleLoginCallback from "./components/GoogleLoginCallback";

export default function App() {

    return (

        <Router>
            <Routes>
                <Route path="/" element= {<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/callback" element={<GoogleLoginCallback />} />
                <Route path="/dashboard" element={<PrivateRoute>
                    <Landing />
                </PrivateRoute>} />
                <Route path="/add" element={<PrivateRoute>
                    <NewMember />
                </PrivateRoute>} />
                <Route path="/edit/:id" element={
                    <PrivateRoute>
                        <Edit />
                    </PrivateRoute>}
                />
                <Route path="/detail/:id" element={
                    <PrivateRoute>
                        <Detail />
                    </PrivateRoute>
                } />
                {/* <Route path="*" element={<NotFound />} /> */}
            </Routes>
        </Router>
    );
}
