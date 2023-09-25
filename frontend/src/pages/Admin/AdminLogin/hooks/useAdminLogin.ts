import { FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';

type FocusType = 'ID' | 'PW';

const useAdminLogin = () => {
  const navigate = useNavigate();
  const [isFocusedId, setIsFocusedId] = useState(false);
  const [isFocusedPw, setIsFocusedPw] = useState(false);

  const loginAdmin = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    navigate('/admin');
  };

  const handleFocus = (type: FocusType) => () => {
    switch (type) {
      case 'ID':
        setIsFocusedId(true);
        setIsFocusedPw(false);
        break;
      case 'PW':
        setIsFocusedId(false);
        setIsFocusedPw(true);
        break;
      default:
        break;
    }
  };

  const handleBlur = () => {
    setIsFocusedId(false);
    setIsFocusedPw(false);
  };

  return { handleBlur, handleFocus, isFocusedId, isFocusedPw, loginAdmin };
};

export default useAdminLogin;
