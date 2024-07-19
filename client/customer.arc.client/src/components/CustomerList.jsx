import React, { useState, useEffect } from 'react';
import axios from '../axiosconfig';

const CustomerList = () => {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const response = await axios.get('/customers');
        setCustomers(response.data);
      } catch (error) {
        console.error('Error fetching customers:', error);
      }
    };

    fetchCustomers();
  }, []);

  const handleDelete = (id) => {
    axios.post(`http://localhost:8080/api/customers/delete/${id}`)
      .then(() => {
        
        setCustomers(customers.filter(customer => customer.id !== id));
      })
      .catch(error => console.error('Error deleting customer:', error));
  };

  return (
    <div>
      <h4 className='text-center'>Customer List</h4>
      <table className="data-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Delete</th>
          
        </tr>
      </thead>
      <tbody>
        {customers.map(customer => (
          <tr key={customer.id}>
            <td>{customer.id}</td>
            <td>{customer.name}</td>
            <td>
              <button className="btn btn-danger" onClick={() => handleDelete(customer.id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
    
    </div>
  );
};

export default CustomerList;