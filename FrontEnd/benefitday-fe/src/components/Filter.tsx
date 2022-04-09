import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { LocalCode, LocalData, IncomeData, PersonalData, FamilyData } from '../data/local';
import { setProvince, setCity, setIncome, setPersonal, setFamily, setBirthday } from '../modules/filter';
import { useDispatch, useSelector } from 'react-redux';
import './Filter.css';
import { RootState } from '../app/store';
import { API } from '../util/api';
import {
  Box,
  Container,
  Stack,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Chip,
  Button,
  TextField,
} from '@mui/material';

function Filter() {
  const [metroCode, setMetroCode] = useState(-1);
  const [birth, setBirth] = useState<Date | null>(new Date());

  const province = useSelector((state: RootState) => state.filter.province);
  const city = useSelector((state: RootState) => state.filter.city);
  const income = useSelector((state: RootState) => state.filter.income);
  const personal = useSelector((state: RootState) => state.filter.personal);
  const family = useSelector((state: RootState) => state.filter.family);
  const birthday = useSelector((state: RootState) => state.filter.birthday);
  const dispatch = useDispatch();

  const navigate = useNavigate();

  interface filterResult {
    result: string;
    error: string;
  }

  const submit = () => {
    API.post<object, filterResult>('/user/setInfo', {
      location: province + ' ' + city,
      incomeRange: income,
      personalChar: personal,
      familyChar: family,
      birthday: birthday,
    })
      .then((res) => {
        console.log(res);
        alert('필터가 성공적으로 저장되었어요.');
        API.post<object, filterResult>('/user/recommendList')
        .then((res) => {
          alert("추천 리스트가 성공적으로 생성되었습니다.");
          window.location.reload();
        })
        .catch((err) => alert('오류가 발생했어요. 다시 시도해주세요.'));
        navigate('/main');
      })
      .catch((err) => {
        alert('오류가 발생했어요. 다시 시도해주세요.');
        console.log(err);
      });
  };

  return (
    <React.Fragment>
      <Container style={{ height: '100vh' }} maxWidth={false} sx={{ mt: 5 }}>
        <div className="header">
          <h2>맞춤필터</h2>
        </div>
        <Box display="flex" justifyContent="center" alignItems="center" minHeight="20vh" sx={{ width: '100%' }}>
          <Stack spacing={3} alignItems="center" direction="column" sx={{ width: '100%' }}>
            <Stack spacing={2} alignItems="center" direction="row" sx={{ width: '100%' }}>
              <FormControl fullWidth>
                <InputLabel id="province-label">시·도 선택</InputLabel>
                <Select
                  id="province-info"
                  value={metroCode}
                  sx={{ bgcolor: '#ffffff' }}
                  onChange={(e) => {
                    console.log(e.target);
                    setMetroCode((metroCode) => Number(e.target.value));
                    dispatch(setProvince(LocalData[Number(e.target.value)].name));
                    dispatch(setCity(LocalData[Number(e.target.value)].value[0]));
                  }}
                >
                  {LocalCode.map((code, index) => (
                    <MenuItem key={index} value={code.toString()}>
                      {LocalData[code].name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
              <FormControl fullWidth>
                <InputLabel id="city-label">시/군/구 선택</InputLabel>
                <Select
                  id="city-info"
                  value={city}
                  sx={{ bgcolor: '#ffffff' }}
                  onChange={(e) => {
                    dispatch(setCity(e.target.value));
                  }}
                >
                  {metroCode !== -1 ? (
                    LocalData[metroCode].value.map((city, index) => (
                      <MenuItem key={index.toString()} value={city}>
                        {city}
                      </MenuItem>
                    ))
                  ) : (
                    <MenuItem value="">시·도를 선택해주세요</MenuItem>
                  )}
                </Select>
              </FormControl>
            </Stack>
            <FormControl fullWidth>
              <InputLabel id="income-label">소득구간</InputLabel>
              <Select
                id="income-info"
                value={income}
                sx={{ bgcolor: '#ffffff' }}
                onChange={(e) => {
                  dispatch(setIncome(e.target.value));
                }}
              >
                {IncomeData.map((data, index) => (
                  <MenuItem key={index.toString()} value={data}>
                    {data}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <InputLabel id="personal-label">개인특성</InputLabel>
              <Select
                id="personal-info"
                value={personal}
                sx={{ bgcolor: '#ffffff' }}
                onChange={(e) => {
                  dispatch(setPersonal(e.target.value));
                }}
              >
                {PersonalData.map((data, index) => (
                  <MenuItem key={index.toString()} value={data}>
                    {data}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <InputLabel id="family-label">가구특성</InputLabel>
              <Select
                id="family-info"
                value={family}
                sx={{ bgcolor: '#ffffff' }}
                onChange={(e) => {
                  dispatch(setFamily(e.target.value));
                }}
              >
                {FamilyData.map((data, index) => (
                  <MenuItem key={index.toString()} value={data}>
                    {data}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
            <FormControl fullWidth>
              <TextField
                label="생년월일(YYYY.MM.DD)"
                variant="outlined"
                sx={{ bgcolor: '#ffffff' }}
                onChange={(e) => {
                  dispatch(setBirthday(e.target.value));
                }}
              />
            </FormControl>
            <Stack spacing={2} alignItems="center" direction="row">
              {province !== '' && city !== '' ? <Chip label={`${province} ${city}`} variant="outlined" /> : ''}
              {income !== '' ? <Chip label={income} variant="outlined" /> : ''}
              {personal !== '' ? <Chip label={personal} variant="outlined" /> : ''}
              {family !== '' ? <Chip label={family} variant="outlined" /> : ''}
              {birthday !== '' ? <Chip label={birthday} variant="outlined" /> : ''}
            </Stack>
            <Button
              variant="contained"
              style={{ backgroundColor: '#66a073', borderColor: '#66a073', color: 'white' }}
              onClick={submit}
            >
              시작하기
            </Button>
          </Stack>
        </Box>
      </Container>
    </React.Fragment>
  );
}

export default Filter;
