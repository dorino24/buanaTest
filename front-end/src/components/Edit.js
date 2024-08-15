import React from "react";
import Nav from "./Nav";

import { useParams, useNavigate } from 'react-router-dom';

export default function Edit() {
    const { id } = useParams()
    const [responData, setResponData] = React.useState({})
    const [formData, setFormData] = React.useState({
        name: "",
        position: "",
        reportTo: "",
        photo: null
    })
    function handleChange(event) {
        const { name, value, type, files } = event.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: type === 'file' ? files[0] : value
        }));
    }

    React.useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/api/member/read/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "X-TOKEN-API": localStorage.getItem('token')
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.status}`);
            }

            const result = await response.json();
            setResponData(result);

            if (result.data) {
                setFormData(formData => ({ ...formData, name: result.data.name, position: result.data.position, reportTo: result.data.reportTo, photo: null }))

            }
        };
        fetchData();

    }, [id]);


    async function handleSubmit(event) {
        event.preventDefault();


        const form = new FormData();
        form.append('name', formData.name);
        form.append('position', formData.position);
        form.append('reportTo', formData.reportTo);

        // if (formData.photo) {
        form.append('file', formData.photo);
        // }
        console.log(form)

        try {
            const response = await fetch(`http://localhost:8080/api/member/update/${id}`, {
                method: 'PATCH',
                body: form,
                headers: {

                    "Accept": "application/json",
                    "X-TOKEN-API": localStorage.getItem('token')
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to update data: ${response.status}`);
            }
            if (response.status === 200) {
                window.location.href = "/dashboard";
            }
            console.log("Member updated successfully");
        } catch (error) {
            console.error(error);
        }
    }


    return (
        <div >
           
           <Nav />
            <div className="containerMember">
                <div className="box">
                    <h1>Edit Member</h1>
                    <form onSubmit={handleSubmit}>
                        <label htmlFor="name"><p>Name</p></label>
                        <input
                            type="text"
                            name="name"
                            id="name"
                            placeholder=" name"
                            onChange={handleChange}
                            value={formData.name} />
                        <br />
                        <label htmlFor="position"><p>Position</p></label>
                        <input
                            id="position"
                            type="text"
                            name="position"
                            placeholder=" Position"
                            onChange={handleChange}
                            value={formData.position} />
                        <br />
                        <label htmlFor="reportTo"><p>Report To</p></label>
                        <input
                            id="reportTo"
                            type="text"
                            name="reportTo"
                            placeholder=" Report To"
                            onChange={handleChange}
                            value={formData.reportTo} />
                        <br />
                        <label htmlFor="photo"><p>Picture</p></label>
                        <input
                            id="photo"
                            type="file"
                            name="photo"
                            onChange={handleChange}
                        />
                        <br />
                        <br />
                        <button type="submit">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    );
}