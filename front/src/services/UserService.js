import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; 


const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


const registerUser = async (userData) => {
  try {
    const response = await apiClient.post('/users', userData);
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

export { registerUser, loginUser };
