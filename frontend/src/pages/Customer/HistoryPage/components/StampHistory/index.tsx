import { useQuery } from '@tanstack/react-query';
import HistoryPage, { DATE_PARSE_OPTION } from '../..';
import { getStampHistories } from '../../../../../api/get';
import CustomerLoadingSpinner from '../../../../../components/LoadingSpinner/CustomerLoadingSpinner';
import { StampHistoryType } from '../../../../../types/domain/stamp';
import { transformEntries, sortMapByKey, parseStringDateToKorean } from '../../../../../utils';
import { EmptyList, DateTitle, HistoryList, HistoryItem, CafeName } from '../../style';

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
    queryFn: () => getStampHistories(),
  });
  const title = '스탬프 적립 내역';

  if (stampStatus === 'loading') {
    return (
      <HistoryPage title={title}>
        <CustomerLoadingSpinner />
      </HistoryPage>
    );
  }
  if (stampStatus === 'error') {
    return <HistoryPage title={title}>에러가 발생했습니다. 잠시 후 다시시도해주세요.</HistoryPage>;
  }

  const stampEntries = Array.from(transformStampsToMap(stampData.stampHistories).entries());

  if (stampEntries.length === 0)
    return (
      <HistoryPage title={title}>
        <EmptyList>아직 적립내역이 없어요 🥲</EmptyList>
      </HistoryPage>
    );

  return (
    <HistoryPage title={title}>
      <ul>
        {stampEntries.reverse().map(([key, stamps]) => (
          <li key={key}>
            <DateTitle>{parseStringDateToKorean(key, DATE_PARSE_OPTION)}</DateTitle>
            <HistoryList key={key}>
              {stamps.map((stamp) => (
                <HistoryItem key={stamp.id}>
                  <CafeName>{stamp.cafeName}</CafeName>
                  <span>+{stamp.stampCount}개</span>
                </HistoryItem>
              ))}
            </HistoryList>
          </li>
        ))}
      </ul>
    </HistoryPage>
  );
};

export default StampHistoryPage;
