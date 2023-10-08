import { useQuery } from '@tanstack/react-query';
import { getCafe } from '../api/get';
import { useNavigate } from 'react-router-dom';
import { INVALID_CAFE_ID } from '../constants/magicNumber';

export const useCafeQuery = () => {
  const result = useQuery({
    queryKey: ['cafe'],
    queryFn: async () => await getCafe(),
    staleTime: Infinity,
  });

  return result;
};

export const useCafeId = () => {
  const { status, data } = useCafeQuery();
  let cafeId = INVALID_CAFE_ID;
  if (status === 'success' && data.cafes.length !== 0) {
    cafeId = data.cafes[0].id;
  }
  return { status, cafeId };
};

export const useRedirectRegisterPage = () => {
  const navigate = useNavigate();
  const { status, cafeId } = useCafeId();

  if (cafeId === INVALID_CAFE_ID && status === 'success') {
    alert('카페 등록 후 사용해주세요.');
    navigate('/admin/register-cafe');
  }

  return cafeId;
};
