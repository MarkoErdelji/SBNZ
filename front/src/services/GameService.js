import axios from 'axios';

const BASE_URL = 'http://localhost:8080/games'; 


const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});
export const getUnendedGames  = async (token) => {
  return apiClient.get(`/unended`, {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
};

export const createGame = (userDTOs, token) => {
    return apiClient.post('/', userDTOs, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  };

export const endGame = (gameId, token) => {
    return apiClient.put(`/end/${gameId}`, null, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  };

  export const getUserActiveGameStatistics = async (token) => {
    try {
      const response = await apiClient.get(`/user-statistics`,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response.data;
    } catch (error) {
      throw new Error(error.response.data.message || 'Error fetching user active game statistics.');
    }
  };

  export const getWinners = async (tournamentName) =>{
        return apiClient.get(`/winners/${tournamentName}`)

  }