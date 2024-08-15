import React from "react";
import Nav from "./Nav";

import { useParams, useNavigate } from 'react-router-dom';

export default function Edit() {
    const { id } = useParams()
    const [responData, setResponData] = React.useState({})

    const [imageSrcs, setImageSrcs] = React.useState([]);

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

        };
        fetchData();

    }, [id]);

    React.useEffect(() => {


        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/api/member/picture/${id}`, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "X-TOKEN-API": localStorage.getItem('token')
                }
            })

            const blobs = await response.blob();;
            const imageUrls = URL.createObjectURL(blobs);
            // const imageUrls =URL.createObjectURL(blobs);
            setImageSrcs(imageUrls);
        };
        console.log(imageSrcs)
        fetchData();

    }, [id]);

    return (
        <div >
            <Nav />
            <h1 style={{ textAlign: "center", marginTop: 30 + "px", marginBottom: 50 + "px" }}>Detail Member</h1>
            <div className="containerMember">
                <div className="memberDetail">
                    <div className="boxDetail">
                        <img src={imageSrcs} alt="member" style={{ width: 100 + "%" }} />
                        <h2> {responData.data && responData.data.name}</h2>
                        <p>Position  :  {responData.data && responData.data.position}</p>
                        <p style={{marginBottom:30+"px"}}>Report To :  {responData.data && responData.data.reportTo}</p>
                    </div>
                </div>
            </div>
        </div>
    );
}