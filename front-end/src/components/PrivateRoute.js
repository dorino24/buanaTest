import React from 'react';
import { Navigate } from 'react-router-dom';

export default function PrivateRoute({ children }) {
    const token = localStorage.getItem('token');
    const expiredAt = localStorage.getItem('expiredAt');
    if (expiredAt < Date.now()) {
        localStorage.removeItem('token');
        localStorage.removeItem('expiredAt');
        return <Navigate to="/" />;
    }
    return token ? children : <Navigate to="/" />;
}
