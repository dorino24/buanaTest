import React from "react";

export default function Login() {
    const [formData, setFormData] = React.useState({
        email: "",
        password: "",
    })

    const [response, setResponse] = React.useState({
        data: {
            token: "",
            expiresAt: "",
        },
        errors: "",
    });

    function handleChange(event) {
        const { name, value } = event.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    function handleResponse(data) {
        setResponse(data);
    }



    const handleSubmit = async (event) => {
        event.preventDefault();
        const response = await fetch('http://localhost:8080/api/login', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
            },
            body: JSON.stringify({
                email: formData.email,
                password: formData.password,
            }),
        });
        const data = await response.json();
        handleResponse(data);

        if (response.status === 200) {
            localStorage.setItem('token', data.data.token);
            localStorage.setItem('expiredAt', data.data.expiredAt);

            window.location.href = "/dashboard";
        }
    };

   

    return (
        <div className="welcome">
            <div className="box">
                <h1>Sign In</h1>
                {response.errors && <p className="error">{response.errors}</p>}
                <form onSubmit={handleSubmit}>
                    <label htmlFor="email"><p>Email</p></label>
                    <input
                        type="text"
                        name="email"
                        id="email"
                        placeholder=" Email"
                        onChange={handleChange}
                        value={formData.email} />
                    <br />
                    <label htmlFor="password"><p>Password</p></label>
                    <input
                        id="password"
                        type="password"
                        name="password"
                        placeholder=" Password"
                        onChange={handleChange}
                        value={formData.password} />
                    <br />
                    <br />
                    <button type="submit">Submit</button>
                </form>
                <p>
                    <a href="/register">Create an account</a>
                </p>
                <hr className="garis" ></hr>
                <img className="google" src="\assets\google2.png" width="50px" alt="google"  />
            </div>
        </div>
    );

}