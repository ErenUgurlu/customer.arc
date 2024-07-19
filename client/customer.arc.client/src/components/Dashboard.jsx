import React, { useState } from 'react';
import CustomerList from './CustomerList';
import FileList from './FileList';
import "../App.css";

const Dashboard = () => {
  const [activeTab, setActiveTab] = useState('customers');

  return (
    <div>
      {/* Navbar */}
      <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <button className='nav-button' onClick={() => setActiveTab('customers')}>Customers</button>
        <button className='nav-button' onClick={() => setActiveTab('files')}>Files</button>
      </nav>
      
      {/* Tab Content */}
      <div className="content">
        {activeTab === 'customers' && <CustomerList />}
        {activeTab === 'files' && <FileList />}
      </div>
    </div>
  );
};

export default Dashboard;
