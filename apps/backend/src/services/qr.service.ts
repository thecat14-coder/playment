import QRCode from 'qrcode';

export class QrService {
  async generateDataUrl(data: string): Promise<string> {
    return QRCode.toDataURL(data, {
      width: 400,
      margin: 2,
      errorCorrectionLevel: 'M',
    });
  }

  async generateBuffer(data: string): Promise<Buffer> {
    return QRCode.toBuffer(data, {
      width: 400,
      margin: 2,
      errorCorrectionLevel: 'M',
      type: 'png',
    });
  }
}
