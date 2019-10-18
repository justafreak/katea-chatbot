interface ICarouselItem {
  url: string;
  imageUrl: string;
  name: string;
  price: {
    value: number;
    currency: string;
  };
  rating: string;
}

type IType = 'TEST' | 'QUICKREPLY' | 'CAROUSEL';

interface IIntentResponse {
  message: {
    type: IType;
    reply: string | Array<string> | Array<ICarouselItem>;
  };
  sessionId: string;
}
