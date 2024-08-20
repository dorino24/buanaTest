import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const GoogleLoginCallback = () => {
    const [token, setToken] = useState(null);
    const [expiresAt, setExpiresAt] = useState(null);
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        const query = new URLSearchParams(location.search);
        const tokenFromUrl = query.get('token');
        const expiresAtFromUrl = query.get('expiresAt');

        if (tokenFromUrl && expiresAtFromUrl) {
            setToken(tokenFromUrl);
            setExpiresAt(expiresAtFromUrl);

            localStorage.setItem('token', tokenFromUrl);
            localStorage.setItem('expiredAt', expiresAtFromUrl);
            window.location.href = "/dashboard";
        }
    }, [location.search, navigate]);

    return (
        <div>
            <h1>Processing Google Login...</h1>
        </div>
    );
};

export default GoogleLoginCallback;
