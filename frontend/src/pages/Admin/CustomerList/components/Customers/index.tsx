import Button from '../../../../../components/Button';
import { Spacing } from '../../../../../style/layout/common';
import { Customer } from '../../../../../types/domain/customer';
import { Status } from '../../../../../types/utils';
import { formatDate } from '../../../../../utils';
import {
  Container,
  Badge,
  CustomerBox,
  InfoContainer,
  LeftInfo,
  Name,
  NameContainer,
  RightInfo,
  EmptyCustomers,
  Skeleton,
  ErrorBox,
} from './style';

interface CustomersProps {
  customers: Customer[] | undefined;
  customersStatus: Status;
}

const Customers = ({ customers, customersStatus }: CustomersProps) => {
  if (customersStatus === 'loading')
    return (
      <>
        {[...Array(5)].map((x) => (
          <Skeleton key={x} />
        ))}
      </>
    );

  if (customersStatus === 'error')
    return (
      <>
        <ErrorBox>
          <span>Oops!</span> 데이터를 불러오는 과정에 오류가 생겼어요.
          <Spacing $size={20} />
          <Button variant="secondary">새로 고침</Button>
        </ErrorBox>
      </>
    );

  if (customers?.length === 0)
    return (
      <EmptyCustomers>
        <span>NO RESULT 🥲</span> 아직 고객이 없어요! <br />
        카페를 방문한 고객에게 스탬프를 적립해 보세요.
      </EmptyCustomers>
    );

  return (
    <Container>
      {customers?.map(
        ({
          id,
          nickname,
          stampCount,
          maxStampCount,
          rewardCount,
          isRegistered,
          recentVisitDate,
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
                최근 방문일: {formatDate(recentVisitDate)}
                <br /> 방문 횟수: {visitCount}번
              </InfoContainer>
            </RightInfo>
          </CustomerBox>
        ),
      )}
    </Container>
  );
};

export default Customers;
