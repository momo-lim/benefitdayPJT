import React, { useEffect } from 'react';
import ProfileInfo from '../components/ProfileInfo';

import { useNavigate } from 'react-router';

function Profile() {
  const value = localStorage.getItem('accessToken');
  const navigate = useNavigate();
  useEffect(() => {
    if (!value) {
      navigate('/');
    }
  }, []);
  return (
    <div style={{ height: '100vh' }}>
      <ProfileInfo></ProfileInfo>
    </div>
  );
}

export default Profile;
