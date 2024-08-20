import React from "react";
import Nav from "./Nav";

export default function Landing() {

    const [Data, setData] = React.useState([]);
    const [imageSrcs, setImageSrcs] = React.useState([]);
    const [searchTerm, setSearchTerm] = React.useState("");
    const [filteredImg, setFilteredImg] = React.useState([]);
    const [filteredData, setFilteredData] = React.useState([]);
    const [currentPage, setCurrentPage] = React.useState(1);
    const itemsPerPage = 8;

    React.useEffect(() => {
        const fetchData = async () => {

            const response = await fetch('http://localhost:8080/api/member/read', {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "X-TOKEN-API": localStorage.getItem('token')
                },
            });
            const result = await response.json();
            setData(result.data);

        };

        fetchData();
    }, []);



    React.useEffect(() => {
        if (Data.length === 0) return;

        // const pictureIds = Data.map(item => item.id);
        const pictureIds = Data.map(item => item.id);
        const fetchImages = async () => {
            const fetchPromises = pictureIds.map(id =>
                fetch(`http://localhost:8080/api/member/picture/${id}`,
                    {
                        method: 'GET',
                        headers: {
                            "Content-Type": "application/json",
                            "Accept": "application/json",
                            "X-TOKEN-API": localStorage.getItem('token')
                        }
                    }

                ).then(response => {
                    if (response.ok) {
                        return response.blob();
                    }
                }));

            const blobs = await Promise.all(fetchPromises);
            const imageUrls = blobs.map(blob => URL.createObjectURL(blob));
            const imagesWithIds = pictureIds.map((id, index) => ({
                id,
                url: imageUrls[index]
            }));
            setImageSrcs(imagesWithIds);
        };

        fetchImages();

        // Cleanup function to revoke object URLs
        return () => {
            imageSrcs.forEach(src => URL.revokeObjectURL(src));
        };
    }, [Data]);

    React.useEffect(() => {
        const filtered = Data.filter(member =>
            member.name.toLowerCase().includes(searchTerm.toLowerCase())
        );
        const filteredimage = imageSrcs.filter(item => filtered.map(item => item.id).includes(item.id));
        setFilteredImg(filteredimage);

        setFilteredData(filtered);
        setCurrentPage(1);
    }, [searchTerm, Data, imageSrcs]);


    const handleDelete = async (event, id) => {
        event.preventDefault();
        if (!window.confirm('Are you sure you want to delete this member?')) return;
        const response = await fetch(`http://localhost:8080/api/member/delete/${id}`, {
            method: 'DELETE',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "X-TOKEN-API": localStorage.getItem('token')
            },
        });
        const data = await response.json();
        if (response.status === 200) {
            window.location.href = "/dashboard";
        }
    };



    const indexOfLastItem = currentPage * itemsPerPage;
    const indexOfFirstItem = indexOfLastItem - itemsPerPage;
    const currentData = filteredData.slice(indexOfFirstItem, indexOfLastItem);

    const currentImg = filteredImg.slice(indexOfFirstItem, indexOfLastItem);

    const totalPages = Math.ceil(filteredData.length / itemsPerPage);

    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };
    return (

        <div >
            <Nav />
            <div className="container">
                <div className="search">
                    <input
                        type="text"
                        placeholder="Search by name"
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
                <div className="memberList">
                    {currentData.length > 0 && currentImg.length > 0 ?
                        currentData.map((member, index) => (
                            <div key={index} className="memberItem">
                                <img src={currentImg[index].url} alt={member.name} width="100%" className="picture" />
                                <div className="memberItemContent">
                                    <h3>{member.name}</h3>
                                </div>
                                <div className="memberItemContent2">
                                    <a href={`/edit/${member.id}`}>
                                        <img className="editIcon" src="/assets/edit.png" alt="edit" />
                                    </a>
                                    <a href={`/detail/${member.id}`}>
                                        <img className="editIcon" src="/assets/view.png" alt="view" />
                                    </a>
                                    <button className="sampah" onClick={(event) => handleDelete(event, member.id)}>
                                        <img className="editIcon" src="/assets/Sampah.png" alt="sampah" />
                                    </button>
                                </div>
                            </div>
                        ))
                        : <p>Not Found...</p>
                    }
                </div>
                <div className="pagination">
                    {Array.from({ length: totalPages }, (_, index) => (
                        <button
                            key={index + 1}
                            onClick={() => handlePageChange(index + 1)}
                            className={currentPage === index + 1 ? 'active' : ''}
                        >
                            {index + 1}
                        </button>
                    ))}
                </div>
            </div>

        </div>
    )

}