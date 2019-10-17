interface ICarouselItem {
  url: string;
  imgSrc: string;
  title: string;
  price: {
    value: number;
    currency: string;
  };
  rating: string;
}

type IType = 'text' | 'quickreply' | 'carousel';

interface IIntentResponse {
  message: {
    type: IType;
    reply: string | Array<string> | Array<ICarouselItem>;
  };
  sessionId: string;
}
