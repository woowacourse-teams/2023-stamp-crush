import { Badge, CustomerBox, CustomerContainer, LeftInfo, Name } from './style';

const CustomerList = () => {
  return (
    <>
      <CustomerContainer>
        <CustomerBox>
          <LeftInfo>
            <Name>
              <h1>윤생</h1> <Badge>단골</Badge>
            </Name>
            <span>
              스탬프: 3/8 <br />
              리워드: 2개
            </span>
          </LeftInfo>
        </CustomerBox>
        <CustomerBox>
          <LeftInfo>
            <h1>라잇</h1>
          </LeftInfo>
        </CustomerBox>
      </CustomerContainer>
    </>
  );
};

export default CustomerList;
