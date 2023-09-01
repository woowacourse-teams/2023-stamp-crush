import { QueryKey, useQuery, UseQueryOptions, UseQueryResult } from '@tanstack/react-query';

type BaseSuspendedUseQueryResult<TData> = Omit<
  UseQueryResult<TData, never>,
  | 'data'
  | 'enabled'
  | 'status'
  | 'error'
  | 'isError'
  | 'isLoading'
  | 'isLoadingError'
  | 'isInitialLoading'
  | 'isSuccess'
>;

// useSuspendedQuery의 상태가 success인 경우
type SuspendedUseQueryResultOnSuccess<TData> = BaseSuspendedUseQueryResult<TData> & {
  data: TData;
  status: 'success';
  isSuccess: true;
  isIdle: false;
};

// useSuspendedQuery의 상태가 idle인 경우
type SuspendedUseQueryResultOnIdle<TData> = BaseSuspendedUseQueryResult<TData> & {
  data: undefined;
  status: 'idle';
  isSuccess: false;
  isIdle: true;
};

type SuspendedQueryResult<TData> =
  | SuspendedUseQueryResultOnSuccess<TData>
  | SuspendedUseQueryResultOnIdle<TData>;

type SuspendedUseQueryOptions<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
> = Omit<UseQueryOptions<TQueryFnData, TError, TData, TQueryKey>, 'suspense'>;

function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: SuspendedUseQueryOptions<TQueryFnData, TError, TData, TQueryKey>,
): SuspendedUseQueryResultOnSuccess<TData>;

function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: SuspendedUseQueryOptions<TQueryFnData, TError, TData, TQueryKey> & {
    enabled: true;
  },
): SuspendedUseQueryResultOnSuccess<TData>;

function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: SuspendedUseQueryOptions<TQueryFnData, TError, TData, TQueryKey> & {
    enabled: false;
  },
): SuspendedUseQueryResultOnIdle<TData>;

function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: SuspendedUseQueryOptions<TQueryFnData, TError, TData, TQueryKey>,
): SuspendedUseQueryResultOnSuccess<TData> | SuspendedUseQueryResultOnIdle<TData>;

// 사용할 useSuspendedQuery
function useSuspendedQuery<
  TQueryFnData = unknown,
  TError = unknown,
  TData = TQueryFnData,
  TQueryKey extends QueryKey = QueryKey,
>(
  options: SuspendedUseQueryOptions<TQueryFnData, TError, TData, TQueryKey> & { enabled?: boolean },
): SuspendedQueryResult<TData> {
  const queryResult = useQuery({
    ...options,
    suspense: true,
  });

  return {
    ...queryResult,
    isIdle: 'isIdle' in queryResult ? queryResult.isIdle : false,
  } as SuspendedQueryResult<TData>;
}

export default useSuspendedQuery;
