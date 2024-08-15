import React from "react";


export default function Register() {
    const [formData, setFormData] = React.useState({
        email: "",
        name: "",
        password: "",
    })
    function handleChange(event) {
        const { name, value } = event.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    }

    const handleSubmit= async (event)=> {
        event.preventDefault()
        const response = await fetch('http://localhost:8080/api/register', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
            },
            body: JSON.stringify({
                email: formData.email,
                name: formData.name,
                password: formData.password,
            }),
        });
        // const data = await response.json();
        if (response.status === 200) {
            alert('Register Success!! Please Login!! ');
            window.location.href = "/";
        }

        console.log(formData)
    }

    return (
        <div className="welcome">
            <div className="box">
                <h1>Sign Up</h1>
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
                    <label htmlFor="name"><p>Name</p></label>
                    <input
                        type="text"
                        name="name"
                        id="name"
                        placeholder=" Name"
                        onChange={handleChange}
                        value={formData.name} />
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
                    <a href="/">Login</a>
                </p>
                <hr className="garis" ></hr>
                <a href="https://www.google.com" style={{ marginTop: 20 + 'px' }}>
                    <img src="\assets\google2.png" width="50px" alt="google" />
                </a>

            </div>
        </div>
    );

}