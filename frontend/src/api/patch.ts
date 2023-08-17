import { api, ownerHeader } from '.';
import {
  CafeIdParams,
  CafeInfoReqBody,
  CustomerIdParams,
  MutateReq,
  RewardIdParams,
  RewardReqBody,
} from '../types/api';
import { PARAMS_ERROR_MESSAGE } from '../constants';

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
