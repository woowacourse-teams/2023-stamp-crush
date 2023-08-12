import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { getStampHistorys } from '../../api/get';
import { RewardCafeName, RewardDateTitle, RewardHistoryItem } from '../RewardHistory/style';
import { StampHistoryType } from '../../types';
import { sortMapByKey, transformEntries } from '../../utils';

// TODO: RewardHistory와 타입 선언을 잘만 하면 재사용하게 만들 수 있을 것 같다.
export function concatStampHistoryDate(stamp: StampHistoryType) {
  return { ...stamp, createdAt: stamp.createdAt.replaceAll(':', '') };
}

export function transformStampsToMap(stamps: StampHistoryType[]): Map<string, StampHistoryType[]> {
  const result = new Map<string, StampHistoryType[]>();
  const propertyName = 'createdAt';
  transformEntries(stamps, propertyName, concatStampHistoryDate).forEach((reward) => {
    const [day] = reward[propertyName].split(' ');
    if (!day) return;
    const existStamps = result.has(day) ? (result.get(day) as StampHistoryType[]) : [];

    const newStamps = [
      ...existStamps,
      {
        ...reward,
      },
    ].sort((a, b) => {
      const aTime = a.createdAt.split(' ')[1];
      const bTime = b.createdAt.split(' ')[1];
      return aTime.localeCompare(bTime);
    });

    result.set(day, newStamps);
  });

  return sortMapByKey(result);
}

const StampHistoryPage = () => {
  const { data: stampData, status: stampStatus } = useQuery(['stampHistory'], {
    queryFn: () => getStampHistorys(),
  });

  if (stampStatus === 'error') return <>에러가 발생했습니다.</>;
  if (stampStatus === 'loading') return <>로딩 중입니다.</>;

  const stampEntries = Array.from(transformStampsToMap(stampData.stampHistorys).entries());
  console.log('🚀 ~ file: index.tsx:18 ~ StampHistoryPage ~ stampEntries:', stampEntries);

  return (
    <>
      <SubHeader title="스탬프 적립 내역" />
      <div>
        {stampEntries.map(([key, stamps]) => (
          <div key={key}>
            <RewardDateTitle>{key}</RewardDateTitle>
            <ul key={key}>
              {stamps.map((stamp) => (
                <RewardHistoryItem key={stamp.id}>
                  <RewardCafeName>{stamp.cafeName}</RewardCafeName>
                  <span>+{stamp.stampCount}개</span>
                </RewardHistoryItem>
              ))}
            </ul>
          </div>
        ))}
      </div>
    </>
  );
};

export default StampHistoryPage;
