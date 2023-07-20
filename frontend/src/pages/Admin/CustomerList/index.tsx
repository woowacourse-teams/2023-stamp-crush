import { Badge, CustomerBox, CustomerContainer, LeftInfo, Name, RightInfo } from './style';
import Text from '../../../components/Text';

const CustomerList = () => {
  return (
    <CustomerContainer>
      <Text variant="pageTitle">내 고객 목록</Text>
      <CustomerBox>
        <LeftInfo>
          <Name>
            <h1>윤생</h1> <Badge $isRegistered={false}>임시</Badge>
          </Name>
          <span>
            스탬프: 3/8 <br />
            리워드: 2개
          </span>
        </LeftInfo>
        <RightInfo>
          <span>첫 방문일: 2023년 5월 6일</span>
          <span>방문 횟수: 13번</span>
        </RightInfo>
      </CustomerBox>
      <CustomerBox>
        <LeftInfo>
          <Name>
            <h1>라잇</h1> <Badge $isRegistered={true}>회원</Badge>
          </Name>
          <span>
            스탬프: 3/8 <br />
            리워드: 2개
          </span>
        </LeftInfo>
        <RightInfo>
          <span>첫 방문일: 2023년 5월 6일</span>
          <span>방문 횟수: 15번</span>
        </RightInfo>
      </CustomerBox>
      <CustomerBox>
        <LeftInfo>
          <Name>
            <h1>레고</h1> <Badge $isRegistered={true}>회원</Badge>
          </Name>
          <span>
            스탬프: 3/8 <br />
            리워드: 2개
          </span>
        </LeftInfo>
        <RightInfo>
          <span>첫 방문일: 2023년 5월 6일</span>
          <span>방문 횟수: 15번</span>
        </RightInfo>
      </CustomerBox>
    </CustomerContainer>
  );
};

export default CustomerList;
