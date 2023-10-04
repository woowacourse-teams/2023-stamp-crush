import React from 'react';
import { useNavigate } from 'react-router-dom';
import SubHeader from '../../../components/Header/SubHeader';
import { ROUTER_PATH } from '../../../constants';
import { NavContainer } from '../MyPage/style';
import { NavWrapper } from './style';
import { AiOutlineUserDelete } from '@react-icons/all-files/ai/AiOutlineUserDelete';

// TODO: 추후에 닉네임, 전화번호 변경 옵션 추가
const CUSTOMER_SETTING_OPTIONS = [
  {
    key: 'customerCancellation',
    value: '회원탈퇴',
    icon: <AiOutlineUserDelete />,
  },
];

const CustomerSetting = () => {
  const navigate = useNavigate();

  const navigatePage = (routerPath: string) => () => {
    navigate(routerPath);
  };

  return (
    <>
      <SubHeader title="내 정보 변경" onClickBack={navigatePage(ROUTER_PATH.myPage)} />
      <NavContainer>
        {CUSTOMER_SETTING_OPTIONS.map((option) => (
          <NavWrapper key={option.key} onClick={navigatePage(ROUTER_PATH[option.key])}>
            {option.icon}
            {option.value}
          </NavWrapper>
        ))}
      </NavContainer>
    </>
  );
};

export default CustomerSetting;
