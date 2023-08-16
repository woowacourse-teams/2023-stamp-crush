import { Customer } from '../../../../types';
import { CustomersRes } from '../../../../types/api';
import {
  Badge,
  CustomerBox,
  InfoContainer,
  LeftInfo,
  Name,
  NameContainer,
  RightInfo,
} from '../style';

interface CustomersProps {
  customersData: CustomersRes;
}

const Customers = ({ customersData }: CustomersProps) => {
  return (
    <>
      {customersData.customers.map(
        ({
          id,
          nickname,
          stampCount,
          maxStampCount,
          rewardCount,
          isRegistered,
          firstVisitDate,
          visitCount,
        }: Customer) => (
          <CustomerBox key={id}>
            <LeftInfo>
              <NameContainer>
                <Name>{nickname}</Name>
                <Badge $isRegistered={isRegistered}>{isRegistered ? '회원' : '임시'}</Badge>
              </NameContainer>
              <InfoContainer>
                스탬프: {stampCount}/{maxStampCount} <br />
                리워드: {rewardCount}개
              </InfoContainer>
            </LeftInfo>
            <RightInfo>
              <InfoContainer>
                첫 방문일: {firstVisitDate}
                <br /> 방문 횟수: {visitCount}번
              </InfoContainer>
            </RightInfo>
          </CustomerBox>
        ),
      )}
    </>
  );
};

export default Customers;
