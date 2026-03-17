
export const iso3ToIso2 = {
  USA: 'US',
  GBR: 'GB',
  ESP: 'ES',
  RUS: 'RU',
  UKR: 'UA',
  POR: 'PT',
  IRN: 'IR',
  FRA: 'FR',
  DEU: 'DE',
  ITA: 'IT',
  CHN: 'CN',
  JPN: 'JP',
  KOR: 'KR',
  BRA: 'BR',
  ARG: 'AR',
  MEX: 'MX',
  
}

export function getFlagAlpha2(code3) {
  if (!code3) return null
  const c = String(code3).trim().toUpperCase()
  if (iso3ToIso2[c]) return iso3ToIso2[c]
  
  if (/^[A-Z]{2}$/.test(c)) return c
  
  if (/^[A-Z]{3}$/.test(c)) return c.slice(0, 2)
  return null
}

export function getFlagUrl(code3, size = '24x18') {
  const alpha2 = getFlagAlpha2(code3)
  if (!alpha2) return null
  // flagcdn: https://flagcdn.com/{size}/{code}.png  size ejemplo: 24x18
  return `https://flagcdn.com/${size}/${alpha2.toLowerCase()}.png`
}