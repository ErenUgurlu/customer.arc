import React, { useState, useEffect } from 'react';
import axios from '../axiosconfig';

const FileList = () => {
  const [files, setFiles] = useState([]);

  useEffect(() => {
    const fetchFiles = async () => {
      try {
        const response = await axios.get('/files');
        setFiles(response.data);
      } catch (error) {
        console.error('Error fetching files:', error);
      }
    };

    fetchFiles();
  }, []);

  return (
    <div>
      <h4 className='text-center'>File List</h4>
      <table className="data-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
        {files.map(file => (
          <tr key={file.id}>
            <td>{file.id}</td>
            <td>{file.fileName}</td>
          </tr>
        ))}
      </tbody>
    </table>
    </div>
  );
};

export default FileList;