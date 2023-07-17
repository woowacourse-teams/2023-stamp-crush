import React from 'react';

const CustomerList = () => {
  const fetchTest = async () => {
    const data = await fetch('/todos').then((response) => {
      console.log(response.json());
    });
  };
  fetchTest();
  return <div>CustomerList</div>;
};

export default CustomerList;
