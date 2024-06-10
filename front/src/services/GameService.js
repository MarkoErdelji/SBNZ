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


  export const performGameAction = async (gameId,token,action) => {
    try {
      const response = await apiClient.post(`/${gameId}?action=`+action,{},{
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


  export const getUserReport = async (token,label) => {
    try {
      const response = await apiClient.get("/stats/"+label,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response.data;
    } catch (error) {
      throw new Error(error.response.data.message || 'Error fetching user active game statistics.');
    }
  };
