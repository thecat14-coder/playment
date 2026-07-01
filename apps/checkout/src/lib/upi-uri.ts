/** Extract query string from a canonical `upi://pay?...` URI. */
export function upiQueryString(upiIntent: string): string {
  const prefix = 'upi://pay?';
  if (!upiIntent.startsWith(prefix)) return '';
  return upiIntent.slice(prefix.length);
}

/**
 * Build an app-specific deep link from a canonical UPI intent.
 * Do NOT naively replace `upi://` — schemes like `tez://upi` produce malformed URIs.
 */
export function buildAppLaunchUri(upiIntent: string, scheme: string): string {
  const query = upiQueryString(upiIntent);
  if (!query) return upiIntent;

  switch (scheme) {
    case 'gpay':
    case 'tez':
      return `gpay://upi/pay?${query}`;
    case 'phonepe':
      return `phonepe://pay?${query}`;
    case 'paytmmp':
      return `paytmmp://pay?${query}`;
    case 'upi':
      return upiIntent;
    default:
      return `${scheme}://pay?${query}`;
  }
}

export function logUpiUri(context: string, uri: string): void {
  console.log(`[UPI] ${context}: ${uri}`);
}
