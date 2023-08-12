import { useQuery } from '@tanstack/react-query';
import SubHeader from '../../components/Header/SubHeader';
import { getStampHistorys } from '../../api/get';
import { RewardCafeName, RewardDateTitle, RewardHistoryItem } from '../RewardHistory/style';
import { StampHistoryType } from '../../types';
import { parseStringDateToKorean, sortMapByKey, transformEntries } from '../../utils';
import { DATE_PARSE_OPTION } from '../../constants';

// TODO: RewardHistory와 타입 선언을 잘만 하면 재사용하게 만들 수 있을 것 같다.
export const concatStampHistoryDate = (stamp: StampHistoryType) => {
  return { ...stamp, createdAt: stamp.createdAt.replaceAll(':', '') };
};

export const sortHandlerByTime = (a: StampHistoryType, b: StampHistoryType) => {
  const aTime = a.createdAt.split(' ')[1];
  const bTime = b.createdAt.split(' ')[1];
  return aTime.localeCompare(bTime);
};

export const transformStampsToMap = (
  stampHistories: StampHistoryType[],
): Map<string, StampHistoryType[]> => {
  const result = new Map<string, StampHistoryType[]>();
  const propertyName = 'createdAt';
  transformEntries(stampHistories, propertyName, concatStampHistoryDate).forEach((stampHistory) => {
    const [day] = stampHistory[propertyName].split(' ');
    if (!day) return;
    const existStamps = result.has(day) ? (result.get(day) as StampHistoryType[]) : [];

    const newStamps = [
      ...existStamps,
      {
        ...stampHistory,
      },
    ].sort(sortHandlerByTime);

    result.set(day, newStamps);
  });

  return sortMapByKey(result);
};

const StampHistoryPage = () => {
  const { data: stampData, status: stampStatus } = useQuery(['stampHistory'], {
    queryFn: () => getStampHistorys(),
  });

  if (stampStatus === 'error') return <>에러가 발생했습니다.</>;
  if (stampStatus === 'loading') return <>로딩 중입니다.</>;

  const stampEntries = Array.from(transformStampsToMap(stampData.stampHistorys).entries());
  return (
    <>
      <SubHeader title="스탬프 적립 내역" />
      <div>
        {stampEntries.map(([key, stamps]) => (
          <div key={key}>
            <RewardDateTitle>{parseStringDateToKorean(key, DATE_PARSE_OPTION)}</RewardDateTitle>
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
