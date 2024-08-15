import React from "react";
import Nav from "./Nav";

export default function NewMember() {
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
    
    async function handleSubmit(event) {
        event.preventDefault();
        
        if (!formData.name || !formData.position || !formData.reportTo || !formData.photo) {
            alert('Please fill in all fields and select a photo.');
            return;
        }
        const form = new FormData();
        form.append('name', formData.name);
        form.append('position', formData.position);
        form.append('reportTo', formData.reportTo);
        form.append('file', formData.photo);

        try {
            const response = await fetch(`http://localhost:8080/api/member/create`, {
                method: 'POST',
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
                    <h1>Add Member</h1>
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