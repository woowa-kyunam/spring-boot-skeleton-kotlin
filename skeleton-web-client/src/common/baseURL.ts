export const baseURL = (): string => {
  //TODO api 요청 경로가 없어 이상한 값을 넣어둔 상태, 변경 필요
  if (process.env.NODE_ENV === 'development') return '/';
  return '/production/api/v1';
};
