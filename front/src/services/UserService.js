import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; 


const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


const registerUser = async (userData, token) => {
  try {
    const response = await apiClient.post('/users', userData,{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};


const loginUser = async (loginData) => {
  try {
    const response = await apiClient.post('/users/generateToken', loginData);
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};


const getUsers = async (token) => {
  try {
    const response = await apiClient.get('/users', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    throw error.response.data;
  }
};

export { registerUser, loginUser,getUsers };
