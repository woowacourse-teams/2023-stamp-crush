import { api, ownerHeader } from '.';
import { PARAMS_ERROR_MESSAGE } from '../constants';
import {
  MutateReq,
  RewardReqBody,
  RewardIdParams,
  CustomerIdParams,
  CafeInfoReqBody,
  CafeIdParams,
} from '../types/api/request';

export const patchReward = async ({
  params,
  body,
}: MutateReq<RewardReqBody, RewardIdParams & CustomerIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.patch<RewardReqBody>(
    `/admin/customers/${params.customerId}/rewards/${params.rewardId}`,
    ownerHeader(),
    body,
  );
};

export const patchCafeInfo = async ({ params, body }: MutateReq<CafeInfoReqBody, CafeIdParams>) => {
  if (!params) throw new Error(PARAMS_ERROR_MESSAGE);
  return await api.patch<CafeInfoReqBody>(`/admin/cafes/${params.cafeId}`, ownerHeader(), body);
};
