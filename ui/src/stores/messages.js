import { writable } from 'svelte/store';
import { BOT, HUMAN } from '../constants/author';
import { MSG_TYPE_TEXT, MSG_TYPE_QUICK_REPLY, MSG_TYPE_CAROUSEL } from '../constants/msgType';

export const messages = writable([
  {
    type: MSG_TYPE_TEXT,
    author: BOT,
    reply: 'Hi there! My name is Katea, your virtual travel assistant.'
  },
  {
    type: MSG_TYPE_TEXT,
    author: BOT,
    reply:
      "I'm here to help you finding your perfect accommodation. You can tell me some info about your trip, or we can just chat."
  }
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: HUMAN,
  //   reply: 'Lisbon'
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: "And you'll be there to..."
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: HUMAN,
  //   reply: 'To party my brains out'
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: 'Any travel companions coming with you?'
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: HUMAN,
  //   reply: 'One of my mates. No wives allowed :)'
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: "I see. What's most important for you on this trip?"
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: HUMAN,
  //   reply: 'I want the roof to be on fire'
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: "Hmm. Let's see..."
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: 'Here you go love. How about these'
  // },
  // {
  //   type: MSG_TYPE_CAROUSEL,
  //   author: BOT,
  //   reply: [
  //     {
  //       id: 'd3d46211-fa0c-44d2-939b-2252facb1ec0',
  //       url: 'https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html',
  //       imageUrl:
  //         'https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=',
  //       price: {
  //         value: 2000.51,
  //         currency: 'RON'
  //       },
  //       rating: 5,
  //       name: 'Paradiso Nr. 1'
  //     },
  //     {
  //       id: 'd3d46211-fa0c-44d2-939b-2252facb1ec0',
  //       url: 'https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html',
  //       imageUrl:
  //         'https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=',
  //       price: {
  //         value: 200.5,
  //         currency: 'RON'
  //       },
  //       rating: 3,
  //       name: "La Shafu'"
  //     },
  //     {
  //       id: 'd3d46211-fa0c-44d2-939b-2252facb1ec0',
  //       url: 'https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html',
  //       imageUrl:
  //         'https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=',
  //       price: {
  //         value: 200.5,
  //         currency: 'RON'
  //       },
  //       rating: 1,
  //       name: 'Hanul milei'
  //     },
  //     {
  //       id: 'd3d46211-fa0c-44d2-939b-2252facb1ec0',
  //       url: 'https://www.booking.com/hotel/gb/the-colonnade-london.en-gb.html',
  //       imageUrl:
  //         'https://r-cf.bstatic.com/xdata/images/hotel/square200/122358182.jpg?k=04522139bfae775f531554f2be8a966e14f11880e12c76f58aa7ec31269eb2d2&o=',
  //       price: {
  //         value: 200.5,
  //         currency: 'RON'
  //       },
  //       rating: 4,
  //       name: 'Pe Germania'
  //     }
  //   ]
  // },
  // {
  //   type: MSG_TYPE_TEXT,
  //   author: BOT,
  //   reply: 'Anything you liked or would you like me to dig deeper?'
  // },
  // {
  //   type: MSG_TYPE_QUICK_REPLY,
  //   author: BOT,
  //   reply: ['No. I only need to buy you a drink now', 'Please dig deeper']
  // }
]);

export const storeBotMsg = (type, reply) => {
  messages.update(msgs => [
    ...msgs,
    {
      type,
      author: BOT,
      reply
    }
  ]);
};

export const storeHumanMsg = (type, reply) => {
  messages.update(msgs => [
    ...msgs,
    {
      type,
      author: HUMAN,
      reply
    }
  ]);
};
