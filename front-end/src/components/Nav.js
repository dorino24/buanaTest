import React from "react";

export default function Nav() {

    const [user,setUser] = React.useState();

    React.useEffect(() => {
        const fetchData = async () => {

            const response = await fetch('http://localhost:8080/api/user', {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "X-TOKEN-API": localStorage.getItem('token')
                },
            });
            const result = await response.json();
            setUser(await result.data);

        };
        fetchData();

    }, []);

    async function handleClick(event) {
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/logout', {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "X-TOKEN-API": localStorage.getItem('token')
            },
        });
        if (response.status === 200) {
            localStorage.removeItem('token');
            localStorage.removeItem('expiredAt');
            window.location.href = "/";
        }
    }
    return (
        <nav>
            <a href="/dashboard">

            <h1>Dashboard</h1>
            </a>
            <ul>
                <li>
                    <a href="/add">New Member</a>
                </li>
                <li className="userLogout">
                    <p style={{ marginRight: 5 + 'px' }}>{user === undefined ? "..." : user.name}</p>
                    <p> | </p>
                    <button onClick={handleClick} >logout</button>
                </li>
            </ul>
        </nav>
    );
}